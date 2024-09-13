package com.MindHub.Homebanking;

import com.MindHub.Homebanking.dtos.CardDTO;
import com.MindHub.Homebanking.models.Card;
import com.MindHub.Homebanking.models.CardType;
import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.models.ColorType;
import com.MindHub.Homebanking.repositories.CardRepository;
import com.MindHub.Homebanking.repositories.ClientRepository;
import com.MindHub.Homebanking.services.CardService;
import com.MindHub.Homebanking.utils.UtilMetod;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CardServiceTest {

    @Autowired
    private CardService cardService;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UtilMetod utilMetod;

    // Test para crear una nueva tarjeta para el cliente actual
    @Test
    @Transactional
    public void testCreateCardForCurrentClient() {
        Client client = new Client("John", "Doe", "john.doe@example.com", "123456");
        clientRepository.save(client);  // Guardar el cliente en la base de datos

        // Crear una nueva tarjeta
        CardDTO newCard = cardService.createCardForCurrentClient(client, "DEBIT", "GOLD");
        assertNotNull(newCard);  // Verifica que la tarjeta fue creada
        assertEquals("John Doe", newCard.getCardholder());  // Verifica que el nombre en la tarjeta sea correcto
        assertEquals(CardType.DEBIT, newCard.getType());  // Verifica que el tipo de tarjeta sea correcto
        assertEquals(ColorType.GOLD, newCard.getColor());  // Verifica que el color de la tarjeta sea correcto

        // Verifica que la tarjeta se haya guardado en la base de datos
        Card cardFromDB = (Card) cardRepository.findByNumber(newCard.getNumber()).orElse(null);
        assertNotNull(cardFromDB);
        assertEquals(newCard.getNumber(), cardFromDB.getNumber());
    }

    // Test para verificar que no se puedan crear tarjetas duplicadas
    @Test
    @Transactional
    public void testCreateDuplicateCard() {
        Client client = new Client("Jane", "Doe", "jane.doe@example.com", "654321");
        clientRepository.save(client);

        // Crear una primera tarjeta
        cardService.createCardForCurrentClient(client, "CREDIT", "SILVER");

        // Intentar crear otra tarjeta del mismo tipo y color, lo que debería lanzar una excepción
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cardService.createCardForCurrentClient(client, "CREDIT", "SILVER");
        });

        assertEquals("You already have a card of this type and color", exception.getMessage());
    }

    // Test para validar detalles de la tarjeta
    @Test
    public void testValidateCardDetails() {
        // Verificar que los detalles de la tarjeta son válidos
        assertDoesNotThrow(() -> cardService.validateCardDetails("DEBIT", "GOLD"));

        // Verificar que se lanza una excepción cuando los detalles no son válidos
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cardService.validateCardDetails(null, "GOLD");
        });
        assertEquals("Type and color must be specified", exception.getMessage());
    }
}

