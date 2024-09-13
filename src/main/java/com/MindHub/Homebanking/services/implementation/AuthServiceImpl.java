package com.MindHub.Homebanking.services.implementation;

import com.MindHub.Homebanking.dtos.ClientDTO;
import com.MindHub.Homebanking.dtos.LoginDTO;
import com.MindHub.Homebanking.dtos.RegisterDTO;
import com.MindHub.Homebanking.models.Account;
import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.repositories.AccountRepository;
import com.MindHub.Homebanking.repositories.ClientRepository;
import com.MindHub.Homebanking.services.AuthService;
import com.MindHub.Homebanking.services.ClientService;
import com.MindHub.Homebanking.servicesSecurity.JwtUtilService;
import com.MindHub.Homebanking.utils.UtilMetod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Autowired
    private UtilMetod utilMetod;

    @Autowired
    private ClientService clientService;

    @Override
    public String login(LoginDTO loginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.email());
        return jwtUtilService.generateToken(userDetails);
    }

    @Override
    public Client register(RegisterDTO registerDTO) {
        // Verificar si el cliente ya existe
        Client existingClient = clientService.findByEmail(registerDTO.email());
        if (existingClient != null) {
            throw new IllegalArgumentException("Email already in use");
        }

        // Crear nuevo cliente
        Client client = new Client(
                registerDTO.firstName(),
                registerDTO.lastName(),
                registerDTO.email(),
                passwordEncoder.encode(registerDTO.password()));

        clientRepository.save(client);

        // Generar la cuenta para el nuevo cliente
        Account account = new Account();
        account.setClient(client);
        account.setNumber(utilMetod.generateAccountNumber());
        account.setCreationDate(LocalDate.now());
        accountRepository.save(account);

        return client;
    }

    @Override
    public ClientDTO getCurrentClient(String email) {
        Client client = clientService.findByEmail(email);
        if (client == null) {
            throw new IllegalArgumentException("Client with email " + email + " not found");
        }
        return new ClientDTO(client);
    }
}
