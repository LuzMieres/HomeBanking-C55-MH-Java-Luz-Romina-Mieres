package com.MindHub.Homebanking.controllers;

import com.MindHub.Homebanking.dtos.AccountDTO;
import com.MindHub.Homebanking.dtos.ClientDTO;
import com.MindHub.Homebanking.models.Account;
import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.repositories.AccountRepository;
import com.MindHub.Homebanking.repositories.ClientRepository;
import com.MindHub.Homebanking.utils.UtilMetod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private UtilMetod utilMetod;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<?> createAccountForCurrentClient(Authentication authentication) {
        // Obtener el cliente autenticado
        Client client = clientRepository.findByEmail(authentication.getName());

        // Verifica si el cliente ya tiene tres cuentas
        List<Account> clientAccounts = accountRepository.findByClient(client);
        if (clientAccounts.size() >= 3) {
            return new ResponseEntity<>("You can't have more than 3 accounts", HttpStatus.FORBIDDEN);
        }

        // Crear la cuenta
        Account account = new Account();
        account.setClient(client);
        account.setNumber(utilMetod.generateAccountNumber());  // Usar el método desde UtilMetod
        account.setCreationDate(LocalDate.now());
        accountRepository.save(account);

        // Devolver los datos del cliente y la cuenta recién creada
        Map<String, Object> response = new HashMap<>();
        response.put("client", new ClientDTO(client));
        response.put("account", new AccountDTO(account));
        clientRepository.save(client);
        client.addAccount(account);
        accountRepository.save(account);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return new ResponseEntity<>(accounts
                .stream()
                .map(AccountDTO::new)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneAccountById(@PathVariable Long id) {
        Account account = accountRepository.findById(id).orElse(null);

        if (account == null) {
            return new ResponseEntity<>("The ID does not match with our db, try again", HttpStatus.NOT_FOUND);
        }
        AccountDTO accountDTO = new AccountDTO(account);

        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }
}
