package com.MindHub.Homebanking.services.implementation;

import com.MindHub.Homebanking.dtos.LoanDTO;
import com.MindHub.Homebanking.exceptions.ClientNotFoundException;
import com.MindHub.Homebanking.models.*;
import com.MindHub.Homebanking.repositories.AccountRepository;
import com.MindHub.Homebanking.repositories.ClientLoanRepository;
import com.MindHub.Homebanking.repositories.LoanRepository;
import com.MindHub.Homebanking.repositories.TransactionRepository;
import com.MindHub.Homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();  // Obtiene todos los préstamos de la base de datos
    }

    @Override
    public List<LoanDTO> getAllLoanDTO() {
        List<Loan> loans = loanRepository.findAll();  // Obtiene todos los préstamos
        return loans.stream().map(LoanDTO::new).collect(Collectors.toList());  // Convierte cada préstamo en un DTO
    }

    @Override
    public Loan getLoanById(Long id) {
        return loanRepository.findById(id).orElse(null);  // Busca un préstamo por ID
    }

    @Override
    public LoanDTO getLoanDTO(Loan loan) {
        return new LoanDTO(loan);  // Convierte un objeto Loan a LoanDTO
    }

    @Override
    @Transactional
    public void applyForLoan(String loanName, double amount, int payments, String destinationAccountNumber, Client client) {
        // Buscar el préstamo por nombre
        Loan loan = findLoanByName(loanName);

        validateClient(client);

        // Validaciones
        validateLoanApplication(loan, amount, payments, destinationAccountNumber);

        // Verificar la cuenta de destino
        Account destinationAccount = verifyDestinationAccount(destinationAccountNumber, client);

        // Calcular el monto total del préstamo con la tasa de interés
        double totalAmount = calculateTotalAmount(amount, payments);

        // Aplicar el préstamo al cliente
        applyLoanToClient(loan, amount, payments, destinationAccount, totalAmount, client);
    }

    private Loan findLoanByName(String loanName) {
        Loan loan = loanRepository.findByName(loanName);
        if (loan == null) {
            throw new IllegalArgumentException("Loan not found with name: " + loanName);
        }
        return loan;
    }

    private void validateLoanApplication(Loan loan, double amount, int payments, String destinationAccountNumber) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Please enter a valid amount.");
        }
        if (payments <= 0) {
            throw new IllegalArgumentException("Please enter valid payments.");
        }
        if (destinationAccountNumber.isEmpty()) {
            throw new IllegalArgumentException("Please select a loan destination account.");
        }
        if (amount > loan.getMaxAmount()) {
            throw new IllegalArgumentException("Amount exceeds the maximum allowed for this loan.");
        }
        if (!loan.getPayments().contains(payments)) {
            throw new IllegalArgumentException("Invalid number of payments for this loan.");
        }
    }

    private Account verifyDestinationAccount(String destinationAccountNumber, Client client) {
        Account destinationAccount = accountRepository.findByNumber(destinationAccountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Destination account not found"));

        if (!destinationAccount.getClient().equals(client)) {
            throw new IllegalArgumentException("Destination account does not belong to the authenticated client.");
        }
        return destinationAccount;
    }

    private double calculateTotalAmount(double amount, int payments) {
        double interestRate = getInterestRate(payments);
        return amount * interestRate;
    }

    private double getInterestRate(int payments) {
        if (payments == 12) {
            return 1.20;  // 20%
        } else if (payments > 12) {
            return 1.25;  // 25%
        } else {
            return 1.15;  // 15%
        }
    }

    private void applyLoanToClient(Loan loan, double amount, int payments, Account destinationAccount, double totalAmount, Client client) {
        // Crear la transacción de crédito
        Transaction creditTransaction = new Transaction(TransactionType.CREDIT, totalAmount,
                "Approved " + loan.getName() + " loan.", LocalDateTime.now(), destinationAccount);
        transactionRepository.save(creditTransaction);

        // Actualizar el balance de la cuenta de destino
        destinationAccount.setBalance(destinationAccount.getBalance() + totalAmount);
        accountRepository.save(destinationAccount);

        // Crear y guardar la relación entre el cliente y el préstamo (ClientLoan)
        ClientLoan clientLoan = new ClientLoan(amount, payments, client, loan);
        client.addClientLoan(clientLoan);
        clientLoanRepository.save(clientLoan);
    }

    private void validateClient(Client client) {
        if (client == null) {
            throw new ClientNotFoundException("Client not found");
        }
    }

}