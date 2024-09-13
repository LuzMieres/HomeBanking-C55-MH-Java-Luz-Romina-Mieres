package com.MindHub.Homebanking.services;

import com.MindHub.Homebanking.dtos.AccountDTO;
import com.MindHub.Homebanking.models.Account;
import com.MindHub.Homebanking.models.Client;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AccountService {
    AccountDTO createAccountForCurrentClient(Client client);
    List<AccountDTO> getAllAccounts();
    AccountDTO getAccountById(Long id);
    Client getAuthenticatedClient(Authentication authentication);
    List<AccountDTO> getClientAccounts(Client client);
    Account getAccountByNumber(String accountNumber);
}

