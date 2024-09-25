package com.MindHub.Homebanking.controllers;

import com.MindHub.Homebanking.dtos.ClientDTO;
import com.MindHub.Homebanking.dtos.LoginDTO;
import com.MindHub.Homebanking.dtos.PasswordChangeDTO;
import com.MindHub.Homebanking.dtos.RegisterDTO;
import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.services.AuthService;
import com.MindHub.Homebanking.services.PasswordChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordChangeService passwordChangeService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            String jwt = authService.login(loginDTO);
            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            return new ResponseEntity<>("Email or password invalid", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        try {
            // Intentar registrar al cliente
            Client client = authService.register(registerDTO);
            return ResponseEntity.ok(client);
        } catch (IllegalArgumentException e) {
            // Si el email ya existe, devolver error 403
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            // Manejo de cualquier otro error inesperado
            return new ResponseEntity<>("An error occurred during registration", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/current")
    public ResponseEntity<?> getClient(Authentication authentication) {
        try {
            ClientDTO clientDTO = authService.getCurrentClient(authentication.getName());
            return ResponseEntity.ok(clientDTO);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDTO passwordChangeDTO, Authentication authentication) {
        try {
            // Llamada al servicio de cambio de contraseña
            passwordChangeService.changePassword(authentication.getName(), passwordChangeDTO);
            return ResponseEntity.ok("Contraseña cambiada con éxito.");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
