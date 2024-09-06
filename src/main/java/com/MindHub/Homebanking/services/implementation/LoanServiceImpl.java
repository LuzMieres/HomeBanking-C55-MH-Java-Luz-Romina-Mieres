package com.MindHub.Homebanking.services.implementation;

import com.MindHub.Homebanking.dtos.LoanDTO;
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
        Loan loan = loanRepository.findByName(loanName);
        if (loan == null) {
            throw new IllegalArgumentException("Loan not found with name: " + loanName);
        }
        // Validaciones básicas
        if (amount <= 0) {
            throw new IllegalArgumentException("Please enter a valid amount.");
        }
        if (payments <= 0) {
            throw new IllegalArgumentException("Please enter a valid payments.");
        }
        if(destinationAccountNumber.isEmpty()){
            throw new IllegalArgumentException("Please select a loan destination account.");
        }
        // Verificar que el monto no exceda el máximo permitido
        if (amount > loan.getMaxAmount()) {
            throw new IllegalArgumentException("Amount exceeds the maximum allowed for this loan");
        }

        // Verificar que el número de cuotas esté entre las disponibles
        if (!loan.getPayments().contains(payments)) {
            throw new IllegalArgumentException("Invalid number of payments for this loan");
        }

        // Verificar que la cuenta de destino exista
        Account destinationAccount = accountRepository.findByNumber(destinationAccountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Destination account not found"));

        // Verificar que la cuenta de destino pertenezca al cliente autenticado
        if (!destinationAccount.getClient().equals(client)) {
            throw new IllegalArgumentException("Destination account does not belong to the authenticated client");
        }

        // Calcular la tasa de interés dependiendo de las cuotas
        double interestRate;
        if (payments == 12) {
            interestRate = 1.20;  // 20%
        } else if (payments > 12) {
            interestRate = 1.25;  // 25%
        } else {
            interestRate = 1.15;  // 15%
        }

        // Calcular el monto total del préstamo con la tasa de interés
        double totalAmount = amount * interestRate;

        // Crear la transacción de crédito
        Transaction creditTransaction = new Transaction(TransactionType.CREDIT, totalAmount,  "Approved " + loan.getName() + " loan.", LocalDateTime.now(), destinationAccount);
        transactionRepository.save(creditTransaction);

        // Actualizar el balance de la cuenta de destino
        destinationAccount.setBalance(destinationAccount.getBalance() + totalAmount);
        accountRepository.save(destinationAccount);

        // Crear y guardar la relación entre el cliente y el préstamo (ClientLoan)
        ClientLoan clientLoan = new ClientLoan(amount, payments, client, loan);
        client.addClientLoan(clientLoan);
        clientLoanRepository.save(clientLoan);
    }

}