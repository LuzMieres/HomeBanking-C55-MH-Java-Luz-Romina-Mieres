package com.MindHub.Homebanking.services;

import com.MindHub.Homebanking.dtos.AccountDTO;
import com.MindHub.Homebanking.models.Client;

import java.util.List;

public interface AccountService {
    AccountDTO createAccountForCurrentClient(Client client);
    List<AccountDTO> getAllAccounts();
    AccountDTO getAccountById(Long id);
}

