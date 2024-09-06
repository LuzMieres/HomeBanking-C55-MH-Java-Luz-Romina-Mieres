package com.MindHub.Homebanking.services;

import com.MindHub.Homebanking.dtos.TransferDTO;
import com.MindHub.Homebanking.models.Client;

public interface TransactionService {
    void createTransaction(TransferDTO transferDTO, Client client);
}

