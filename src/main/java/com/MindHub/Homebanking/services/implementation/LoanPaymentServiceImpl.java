package com.MindHub.Homebanking.services.implementation;

import com.MindHub.Homebanking.dtos.LoanPaymentDTO;
import com.MindHub.Homebanking.models.*;
import com.MindHub.Homebanking.repositories.*;
import com.MindHub.Homebanking.services.LoanPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoanPaymentServiceImpl implements LoanPaymentService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Override
    @Transactional
    public void payLoan(String username, LoanPaymentDTO loanPaymentDTO) throws Exception {
        // Obtener el cliente autenticado
        Client client = clientRepository.findByEmail(username)
                .orElseThrow(() -> new Exception("Client not found"));

        // Verificar que la relación préstamo-cliente existe
        ClientLoan clientLoan = clientLoanRepository.findByLoanIdAndClient(loanPaymentDTO.getLoanId(), client)
                .orElseThrow(() -> new Exception("Loan not found or does not belong to client"));

        // Obtener el préstamo asociado
        Loan loan = clientLoan.getLoan();  // Obtener el Loan desde ClientLoan

        // Verificar que la cuenta de origen pertenece al cliente y tiene saldo suficiente
        Account sourceAccount = accountRepository.findByNumberAndClient(loanPaymentDTO.getSourceAccountNumber(), client)
                .orElseThrow(() -> new Exception("Source account not found or does not belong to client"));

        if (sourceAccount.getBalance() < loanPaymentDTO.getAmount()) {
            throw new Exception("Insufficient balance in source account");
        }

        // Crear transacción de débito desde la cuenta del cliente
        Transaction debitTransaction = new Transaction(TransactionType.DEBIT, -loanPaymentDTO.getAmount(),
                "Loan payment - " + loan.getName(), sourceAccount);
        transactionRepository.save(debitTransaction);

        // Actualizar el balance de la cuenta del cliente
        sourceAccount.setBalance(sourceAccount.getBalance() - loanPaymentDTO.getAmount());
        accountRepository.save(sourceAccount);

        // Actualizar la cantidad de pagos restantes del préstamo
        int remainingPayments = clientLoan.getRemainingPayments() - 1; // Restar una cuota al préstamo

        if (remainingPayments < 0) {
            throw new Exception("No remaining payments left for this loan");
        }

        clientLoan.setRemainingPayments(remainingPayments); // Actualizar los pagos restantes en ClientLoan
        clientLoanRepository.save(clientLoan); // Guardar la entidad actualizada
    }

}
