package com.MindHub.Homebanking.controllers;

import com.MindHub.Homebanking.dtos.CardDTO;
import com.MindHub.Homebanking.dtos.ClientDTO;
import com.MindHub.Homebanking.models.Card;
import com.MindHub.Homebanking.models.CardType;
import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.models.ColorType;
import com.MindHub.Homebanking.repositories.CardRepository;
import com.MindHub.Homebanking.repositories.ClientRepository;
import com.MindHub.Homebanking.utils.UtilMetod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private UtilMetod utilMetod;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<?> createCardForCurrentClient(Authentication authentication, @RequestBody Map<String, String> cardDetails) {
        // Verificar si faltan los parámetros tipo o color
        String typeString = cardDetails.get("type");
        String colorString = cardDetails.get("color");

        if (typeString == null) {
            return new ResponseEntity<>("Card type must be specified", HttpStatus.FORBIDDEN);
        }
        if (colorString == null) {
            return new ResponseEntity<>("Card color must be specified", HttpStatus.FORBIDDEN);
        }

        CardType type = CardType.valueOf(typeString.toUpperCase());
        ColorType color = ColorType.valueOf(colorString.toUpperCase());

        // Obtener el cliente autenticado
        Client client = clientRepository.findByEmail(authentication.getName());

        // Verifica si ya existe una tarjeta del mismo color y tipo
        List<Card> clientCards = cardRepository.findByClientAndColorAndType(client, color, type);
        if (!clientCards.isEmpty()) {
            return new ResponseEntity<>("You already have a card of this type and color", HttpStatus.FORBIDDEN);
        }

        // Verificar si el cliente ya tiene el máximo permitido de tarjetas por color y tipo
        long countCardsByColorAndType = cardRepository.countByClientAndColorAndType(client, color, type);
        if (countCardsByColorAndType >= 1) {
            return new ResponseEntity<>("You can't have more than one " + type + " card of color " + color, HttpStatus.FORBIDDEN);
        }

        // Crear la tarjeta
        Card card = new Card();
        card.setClient(client);
        card.setType(type);
        card.setColor(color);
        card.setNumber(utilMetod.generateCardNumber());
        card.setCvv(utilMetod.generateCvv());
        card.setFromDate(LocalDate.now());
        card.setThruDate(utilMetod.getThruDate());
        card.setCardHolder(client.getFirstName() + " " + client.getLastName()); // Establecer el nombre del titular de la tarjeta
        cardRepository.save(card);

        // Devolver los datos del cliente y la tarjeta recién creada
        Map<String, Object> response = new HashMap<>();
        response.put("client", new ClientDTO(client));
        response.put("card", new CardDTO(card));
        clientRepository.save(client);
        client.addCard(card);
        cardRepository.save(card);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
