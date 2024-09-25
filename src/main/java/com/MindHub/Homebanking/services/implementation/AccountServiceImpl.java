package com.MindHub.Homebanking.services.implementation;

import com.MindHub.Homebanking.dtos.AccountDTO;
import com.MindHub.Homebanking.models.Account;
import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.repositories.AccountRepository;
import com.MindHub.Homebanking.services.AccountService;
import com.MindHub.Homebanking.services.ClientService;
import com.MindHub.Homebanking.utils.UtilMetod;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UtilMetod utilMetod;

    @Autowired
    private ClientService clientService;

    @Override
    public AccountDTO createAccountForCurrentClient(Client client) {
        validateMaxAccounts(client);

        Account account = new Account();
        client.addAccount(account);
        account.setNumber(utilMetod.generateAccountNumber());
        account.setCreationDate(LocalDate.now());
        accountRepository.save(account);

        return new AccountDTO(account);
    }

    private void validateMaxAccounts(Client client) {
        if (accountRepository.findByClient(client).size() >= 3) {
            throw new IllegalArgumentException("You can't have more than 3 accounts");
        }
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(AccountDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDTO getAccountById(Long id) {
        return accountRepository.findById(id)
                .map(AccountDTO::new)
                .orElseThrow(() -> new IllegalArgumentException("The account does not exist"));
    }

    // Método para obtener la cuenta por númer

    @Transactional
    public Account getAccountByNumber(String accountNumber) throws Exception {
            // Buscar la cuenta por su número
        List<Account> account = accountRepository.findByNumber(accountNumber);

            // Si no se encuentra, lanzar excepción
        if (account.isEmpty()) {
            throw new Exception("Account not found");
        }

        return account.get(0); // Retornar la cuenta encontrada
    }

    @Override
    public Client getAuthenticatedClient(Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());
        validateExistClient(client);
        return client;
    }

    private void validateExistClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("The client does not exist");
        }
    }

    @Override
    public List<AccountDTO> getClientAccounts(Client client) {
        return client.getAccounts()
                .stream()
                .map(AccountDTO::new)
                .collect(Collectors.toList());
    }
}
