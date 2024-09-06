package com.MindHub.Homebanking.services;

import com.MindHub.Homebanking.dtos.CardDTO;
import com.MindHub.Homebanking.models.Client;

public interface CardService {
    CardDTO createCardForCurrentClient(Client client, String type, String color);
}

