package com.MindHub.Homebanking.controllers;

import com.MindHub.Homebanking.dtos.TransferDTO;
import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.services.ClientService;
import com.MindHub.Homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientService clientService;

    @PostMapping("/")
    public ResponseEntity<?> createTransaction(@RequestBody TransferDTO transferDTO, Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());

        try {
            transactionService.createTransaction(transferDTO, client);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}

