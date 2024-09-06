package com.MindHub.Homebanking.services;

import com.MindHub.Homebanking.dtos.ClientDTO;
import com.MindHub.Homebanking.models.Client;

import java.util.List;

public interface ClientService {
    List<Client> getAllClient();
    List<ClientDTO> getAllClientDTO();
    Client getClientById(Long id);
    ClientDTO getClientDTO(Client client);
    Client findByEmail(String email);
}
