package com.MindHub.Homebanking.controllers;

import com.MindHub.Homebanking.dtos.ClientDTO;
import com.MindHub.Homebanking.dtos.LoginDTO;
import com.MindHub.Homebanking.dtos.RegisterDTO;
import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.repositories.AccountRepository;
import com.MindHub.Homebanking.repositories.ClientRepository;
import com.MindHub.Homebanking.servicesSecurity.JwtUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.email());
            final String jwt = jwtUtilService.generateToken(userDetails);

            Map<String, String> response = new HashMap<>();
            response.put("token", jwt);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>("Email or password invalid", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        if (registerDTO.firstName().isBlank()) {
            return new ResponseEntity<>("First name field must not be empty", HttpStatus.FORBIDDEN);
        }
        if (registerDTO.lastName().isBlank()) {
            return new ResponseEntity<>("Last lastname field must not be empty", HttpStatus.FORBIDDEN);
        }
        if (registerDTO.email().isBlank()) {
            return new ResponseEntity<>("Email field must not be empty", HttpStatus.FORBIDDEN);
        }
        if (clientRepository.findByEmail(registerDTO.email()) != null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }
        if (registerDTO.password().isBlank()) {
            return new ResponseEntity<>("Password field must not be empty", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(
                registerDTO.firstName(),
                registerDTO.lastName(),
                registerDTO.email(),
                passwordEncoder.encode(registerDTO.password()));
        clientRepository.save(client);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/current")
    public ResponseEntity<?> getClient(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        return ResponseEntity.ok(new ClientDTO(client));
    }

}
