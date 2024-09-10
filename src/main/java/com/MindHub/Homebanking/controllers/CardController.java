package com.MindHub.Homebanking.controllers;

import com.MindHub.Homebanking.dtos.CardDTO;
import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.services.CardService;
import com.MindHub.Homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<?> createCardForCurrentClient(Authentication authentication, @RequestBody Map<String, String> cardDetails) {
        String type = cardDetails.get("type");
        String color = cardDetails.get("color");
        try {
            cardService.validateCardDetails(type, color);
            Client client = clientService.findByEmail(authentication.getName());
            CardDTO cardDTO = cardService.createCardForCurrentClient(client, type, color);
            return new ResponseEntity<>(cardDTO, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}

