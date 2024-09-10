package com.MindHub.Homebanking.services.implementation;

import com.MindHub.Homebanking.dtos.TransferDTO;
import com.MindHub.Homebanking.models.Account;
import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.models.Transaction;
import com.MindHub.Homebanking.models.TransactionType;
import com.MindHub.Homebanking.repositories.AccountRepository;
import com.MindHub.Homebanking.repositories.TransactionRepository;
import com.MindHub.Homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    @Transactional
    public void createTransaction(TransferDTO transferDTO, Client client) {
        double amount = transferDTO.getAmount();
        String originAccountNumber = transferDTO.getOriginAccountNumber();
        String destinationAccountNumber = transferDTO.getDestinationAccountNumber();

        // Obtener cuentas
        Account originAccount = getAccountByNumber(originAccountNumber, "Origin account does not exist");
        Account destinationAccount = getAccountByNumber(destinationAccountNumber, "Destination account does not exist");

        // Validaciones
        validateClientAccountOwnership(originAccount, client);
        validateSufficientBalance(originAccount, amount);

        // Crear y guardar las transacciones
        processTransaction(amount, originAccountNumber, destinationAccountNumber, originAccount, destinationAccount);
    }

    // Método para obtener la cuenta por número
    private Account getAccountByNumber(String accountNumber, String errorMessage) {
        return accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException(errorMessage));
    }

    // Método para validar que la cuenta de origen pertenezca al cliente autenticado
    private void validateClientAccountOwnership(Account originAccount, Client client) {
        if (!originAccount.getClient().equals(client)) {
            throw new IllegalArgumentException("Origin account does not belong to the authenticated client");
        }
    }

    // Método para validar que la cuenta tenga saldo suficiente
    private void validateSufficientBalance(Account originAccount, double amount) {
        if (originAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }
    }

    // Método para procesar la transacción
    private void processTransaction(double amount, String originAccountNumber, String destinationAccountNumber, Account originAccount, Account destinationAccount) {
        Transaction debitTransaction = createTransaction(TransactionType.DEBIT, -amount, "Transfer to " + destinationAccountNumber, originAccount);
        Transaction creditTransaction = createTransaction(TransactionType.CREDIT, amount, "Transfer from " + originAccountNumber, destinationAccount);

        // Guardar transacciones
        transactionRepository.save(debitTransaction);
        transactionRepository.save(creditTransaction);

        // Actualizar balances
        updateAccountBalances(originAccount, destinationAccount, amount);
    }

    // Método para crear una transacción
    private Transaction createTransaction(TransactionType type, double amount, String description, Account account) {
        return new Transaction(type, amount, description, LocalDateTime.now(), account);
    }

    // Método para actualizar los balances de las cuentas
    private void updateAccountBalances(Account originAccount, Account destinationAccount, double amount) {
        originAccount.setBalance(originAccount.getBalance() - amount);
        destinationAccount.setBalance(destinationAccount.getBalance() + amount);

        accountRepository.save(originAccount);
        accountRepository.save(destinationAccount);
    }
}

