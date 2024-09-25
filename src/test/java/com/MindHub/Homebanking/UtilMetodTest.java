//package com.MindHub.Homebanking;
//
//import com.MindHub.Homebanking.repositories.AccountRepository;
//import com.MindHub.Homebanking.utils.UtilMetod; // Importa UtilMetod
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@SpringBootTest
//@ActiveProfiles("test")  // Usar el perfil de prueba
//class UtilMetodTest {
//
//    @Autowired
//    private UtilMetod utilMetod; // Deja que Spring maneje la inyección
//
//    @Autowired
//    private AccountRepository accountRepository; // Ya no necesitas inyectar manualmente
//
//    @BeforeEach
//    void setUp() {
//        // Spring inyectará automáticamente los beans, no es necesario asignarlos aquí
//    }
//
//    @Test
//    void testGenerateAccountNumber() {
//        String accountNumber = utilMetod.generateAccountNumber();
//        assertNotNull(accountNumber);
//        assertTrue(accountNumber.startsWith("VIN"));
//    }
//
//    @Test
//    void testGenerateCardNumber() {
//        String cardNumber = utilMetod.generateCardNumber();
//        assertNotNull(cardNumber);
//        assertEquals(19, cardNumber.length()); // Formato de 16 dígitos y 3 espacios
//    }
//
//    @Test
//    void testGenerateCvv() {
//        String cvv = utilMetod.generateCvv();
//        assertNotNull(cvv);
//        assertEquals(3, cvv.length());
//    }
//
//    @Test
//    void testGetThruDate() {
//        LocalDate thruDate = utilMetod.getThruDate();
//        assertNotNull(thruDate);
//        assertEquals(LocalDate.now().plusYears(5), thruDate);
//    }
//}
