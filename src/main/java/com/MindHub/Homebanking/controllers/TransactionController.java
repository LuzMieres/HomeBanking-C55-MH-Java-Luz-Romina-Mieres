package com.MindHub.Homebanking.controllers;

import com.MindHub.Homebanking.dtos.TransferDTO;
import com.MindHub.Homebanking.models.Account;
import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.models.Transaction;
import com.MindHub.Homebanking.models.TransactionType;
import com.MindHub.Homebanking.repositories.AccountRepository;
import com.MindHub.Homebanking.repositories.ClientRepository;
import com.MindHub.Homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    @PostMapping("/transfer")
    public ResponseEntity<Object> createTransaction(
            @RequestBody TransferDTO transferDTO,
            Authentication authentication) {

        double amount = transferDTO.getAmount();
        String description = transferDTO.getDescription();
        String originAccountNumber = transferDTO.getOriginAccountNumber();
        String destinationAccountNumber = transferDTO.getDestinationAccountNumber();
        {

            if (amount <= 0 || description.isEmpty()) {
                return new ResponseEntity<>("Amount or description cannot be empty", HttpStatus.FORBIDDEN);
            }

            if (originAccountNumber.isEmpty() || destinationAccountNumber.isEmpty()) {
                return new ResponseEntity<>("Account number cannot be empty", HttpStatus.FORBIDDEN);
            }

            Account originAccount = accountRepository.findByNumber(originAccountNumber).orElse(null);
            if (originAccount == null) {
                return new ResponseEntity<>("Origin account does not exist", HttpStatus.FORBIDDEN);
            }

            Account destinationAccount = accountRepository.findByNumber(destinationAccountNumber).orElse(null);
            if (destinationAccount == null) {
                return new ResponseEntity<>("Destination account does not exist", HttpStatus.FORBIDDEN);
            }

// Obtener el cliente autenticado usando el método en ClientRepository
            Client client = clientRepository.findByEmail(authentication.getName());
            if (client == null) {
                return new ResponseEntity<>("Authenticated client not found", HttpStatus.FORBIDDEN);
            }

            if (!originAccount.getClient().equals(client)) {
                return new ResponseEntity<>("Origin account does not belong to the authenticated client", HttpStatus.FORBIDDEN);
            }

            if (originAccount.getBalance() < amount) {
                return new ResponseEntity<>("Insufficient balance", HttpStatus.FORBIDDEN);
            }

            if (originAccountNumber.equals(destinationAccountNumber)) {
                return new ResponseEntity<>("Origin account cannot be the same as the destination account", HttpStatus.FORBIDDEN);
            }


            Transaction debitTransaction = new Transaction(TransactionType.DEBIT, -amount, description + " " + destinationAccountNumber, LocalDateTime.now(), originAccount);
            Transaction creditTransaction = new Transaction(TransactionType.CREDIT, amount, description + " " + originAccountNumber, LocalDateTime.now(), destinationAccount);

            transactionRepository.save(debitTransaction);
            transactionRepository.save(creditTransaction);

            originAccount.setBalance(originAccount.getBalance() - amount);
            destinationAccount.setBalance(destinationAccount.getBalance() + amount);

            accountRepository.save(originAccount);
            accountRepository.save(destinationAccount);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}
