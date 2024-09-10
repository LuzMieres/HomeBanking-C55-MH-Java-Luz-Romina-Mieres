package com.MindHub.Homebanking.services.implementation;

import com.MindHub.Homebanking.dtos.CardDTO;
import com.MindHub.Homebanking.models.Card;
import com.MindHub.Homebanking.models.CardType;
import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.models.ColorType;
import com.MindHub.Homebanking.repositories.CardRepository;
import com.MindHub.Homebanking.repositories.ClientRepository;
import com.MindHub.Homebanking.services.CardService;
import com.MindHub.Homebanking.utils.UtilMetod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private UtilMetod utilMetod;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public CardDTO createCardForCurrentClient(Client client, String type, String color) {
        CardType cardType = CardType.valueOf(type.toUpperCase());
        ColorType cardColor = ColorType.valueOf(color.toUpperCase());

        // Verificar si ya existe una tarjeta de este tipo y color
        checkIfCardAlreadyExists(client, cardColor, cardType);

        Card card = new Card();
        card.setClient(client);
        card.setType(cardType);
        card.setColor(cardColor);
        card.setNumber(utilMetod.generateCardNumber());
        card.setCvv(utilMetod.generateCvv());
        card.setFromDate(LocalDate.now());
        card.setThruDate(utilMetod.getThruDate());
        card.setCardHolder(client.getFirstName() + " " + client.getLastName());
        cardRepository.save(card);

        return new CardDTO(card);
    }

    @Override
    public void validateCardDetails(String type, String color) {
        if (type == null || color == null) {
            throw new IllegalArgumentException("Type and color must be specified");
        }
    }

    private void checkIfCardAlreadyExists(Client client, ColorType cardColor, CardType cardType) {
        List<Card> clientCards = cardRepository.findByClientAndColorAndType(client, cardColor, cardType);
        if (!clientCards.isEmpty()) {
            throw new IllegalArgumentException("You already have a card of this type and color");
        }
    }
}

