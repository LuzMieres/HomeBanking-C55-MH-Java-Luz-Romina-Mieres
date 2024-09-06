package com.MindHub.Homebanking.services;

import com.MindHub.Homebanking.dtos.ClientDTO;
import com.MindHub.Homebanking.dtos.LoginDTO;
import com.MindHub.Homebanking.dtos.RegisterDTO;
import com.MindHub.Homebanking.models.Client;

public interface AuthService {
    String login(LoginDTO loginDTO);
    Client register(RegisterDTO registerDTO);
    ClientDTO getCurrentClient(String email);
}