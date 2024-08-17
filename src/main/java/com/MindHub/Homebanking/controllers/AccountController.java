package com.MindHub.Homebanking.controllers;

import com.MindHub.Homebanking.dtos.AccountDTO;
import com.MindHub.Homebanking.models.Account;
import com.MindHub.Homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/")
    public ResponseEntity<List<AccountDTO>> getAllAccounts(){
        List<Account> accounts = (List<Account>) accountRepository.findAll();

        return new ResponseEntity<>(accounts.stream().map(AccountDTO::new).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneAccountById(@PathVariable Long id){
        Account account = accountRepository.findById(id).orElse(null);

        if(account == null) {
            return new ResponseEntity<>("The ID does not match with our db, try again", HttpStatus.NOT_FOUND);
        }
        AccountDTO accountDTO = new AccountDTO(account);

        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }
}
