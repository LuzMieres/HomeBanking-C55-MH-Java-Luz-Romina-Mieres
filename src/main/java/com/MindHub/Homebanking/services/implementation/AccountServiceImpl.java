package com.MindHub.Homebanking.services.implementation;

import com.MindHub.Homebanking.dtos.AccountDTO;
import com.MindHub.Homebanking.models.Account;
import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.repositories.AccountRepository;
import com.MindHub.Homebanking.services.AccountService;
import com.MindHub.Homebanking.utils.UtilMetod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UtilMetod utilMetod;

    @Override
    public AccountDTO createAccountForCurrentClient(Client client) {
        if (accountRepository.findByClient(client).size() >= 3) {
            throw new IllegalArgumentException("You can't have more than 3 accounts");
        }

        Account account = new Account();
        account.setClient(client);
        account.setNumber(utilMetod.generateAccountNumber());
        account.setCreationDate(LocalDate.now());
        accountRepository.save(account);

        return new AccountDTO(account);
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
}
