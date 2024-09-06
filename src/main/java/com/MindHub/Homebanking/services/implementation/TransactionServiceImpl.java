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

        Account originAccount = accountRepository.findByNumber(originAccountNumber).orElseThrow(() ->
                new IllegalArgumentException("Origin account does not exist"));

        Account destinationAccount = accountRepository.findByNumber(destinationAccountNumber).orElseThrow(() ->
                new IllegalArgumentException("Destination account does not exist"));

        if (!originAccount.getClient().equals(client)) {
            throw new IllegalArgumentException("Origin account does not belong to the authenticated client");
        }

        if (originAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        Transaction debitTransaction = new Transaction(TransactionType.DEBIT, -amount, "Transfer to " + destinationAccountNumber, LocalDateTime.now(), originAccount);
        Transaction creditTransaction = new Transaction(TransactionType.CREDIT, amount, "Transfer from " + originAccountNumber, LocalDateTime.now(), destinationAccount);

        transactionRepository.save(debitTransaction);
        transactionRepository.save(creditTransaction);

        originAccount.setBalance(originAccount.getBalance() - amount);
        destinationAccount.setBalance(destinationAccount.getBalance() + amount);

        accountRepository.save(originAccount);
        accountRepository.save(destinationAccount);
    }
}

