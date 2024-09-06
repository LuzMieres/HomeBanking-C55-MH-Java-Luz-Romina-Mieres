package com.MindHub.Homebanking.controllers;

import com.MindHub.Homebanking.dtos.AccountDTO;
import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.services.AccountService;
import com.MindHub.Homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<?> createAccountForCurrentClient(Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());

        try {
            AccountDTO accountDTO = accountService.createAccountForCurrentClient(client);
            return new ResponseEntity<>(accountDTO, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<AccountDTO> accounts = accountService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneAccountById(@PathVariable Long id) {
        try {
            AccountDTO accountDTO = accountService.getAccountById(id);
            return new ResponseEntity<>(accountDTO, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
