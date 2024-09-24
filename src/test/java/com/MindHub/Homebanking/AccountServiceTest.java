package com.MindHub.Homebanking;

import com.MindHub.Homebanking.dtos.AccountDTO;
import com.MindHub.Homebanking.models.Account;
import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.repositories.AccountRepository;
import com.MindHub.Homebanking.repositories.ClientRepository;
import com.MindHub.Homebanking.services.AccountService;
import com.MindHub.Homebanking.services.ClientService;
import com.MindHub.Homebanking.utils.UtilMetod;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private UtilMetod utilMetod;

    @Autowired
    private ClientRepository clientRepository;

    // Test para crear una nueva cuenta para un cliente autenticado
    @Test
    @Transactional
    public void testCreateAccountForCurrentClient() {
        // Crear cliente si no existe
        Client client = clientService.findByEmail("testuser@example.com");
        if (client == null) {
            client = new Client("Test", "User", "testuser@example.com", "password");
            clientRepository.saveAndFlush(client);  // Asegúrate de guardar el cliente
        }

        assertNotNull(client);  // Verifica que el cliente exista

        AccountDTO newAccount = accountService.createAccountForCurrentClient(client);
        assertNotNull(newAccount);  // Verifica que la cuenta fue creada
        assertEquals(1, client.getAccounts().size());  // Verifica que el cliente tenga la nueva cuenta asociada
    }

    // Test para validar que un cliente no puede tener más de 3 cuentas
    @Test
    @Transactional
    public void testMaxAccountsValidation() {
        // Crear cliente si no existe
        Client client = clientService.findByEmail("testuser@example.com");
        if (client == null) {
            client = new Client("Test", "User", "testuser@example.com", "password");
            clientRepository.saveAndFlush(client);  // Asegúrate de guardar el cliente
        }

        assertNotNull(client);

        // Crear 3 cuentas para el cliente
        for (int i = 0; i < 3; i++) {
            accountService.createAccountForCurrentClient(client);
        }

        // Intentar crear una cuarta cuenta, lo que debería lanzar una excepción
        Client finalClient = client;
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.createAccountForCurrentClient(finalClient);
        });

        assertEquals("You can't have more than 3 accounts", exception.getMessage());
    }

    // Test para obtener todas las cuentas en el sistema
    @Test
    @Transactional
    public void testGetAllAccounts() {
        List<AccountDTO> allAccounts = accountService.getAllAccounts();
        assertNotNull(allAccounts);
        assertTrue(allAccounts.size() > 0);  // Verifica que haya cuentas en la base de datos
    }

    // Test para obtener una cuenta por ID
    @Test
    @Transactional
    public void testGetAccountById() {
        Account account = new Account();
        account.setNumber(utilMetod.generateAccountNumber());
        account.setCreationDate(LocalDate.now());
        accountRepository.saveAndFlush(account);

        AccountDTO accountDTO = accountService.getAccountById(account.getId());
        assertNotNull(accountDTO);
        assertEquals(account.getId(), accountDTO.getId());  // Verifica que la cuenta obtenida sea la correcta
    }

    // Test para obtener una cuenta por número
    @Test
    @Transactional
    public void testGetAccountByNumber() {
        Account account = new Account();
        account.setNumber(utilMetod.generateAccountNumber());
        account.setCreationDate(LocalDate.now());
        accountRepository.saveAndFlush(account);

        Account foundAccount = accountService.getAccountByNumber(account.getNumber());
        assertNotNull(foundAccount);
        assertEquals(account.getNumber(), foundAccount.getNumber());  // Verifica que la cuenta obtenida por número es correcta
    }

    // Test para obtener las cuentas de un cliente
    @Test
    @Transactional
    public void testGetClientAccounts() {
        // Crear cliente si no existe
        Client client = clientService.findByEmail("testuser@example.com");
        if (client == null) {
            client = new Client("Test", "User", "testuser@example.com", "password");
            clientRepository.saveAndFlush(client);  // Asegúrate de guardar el cliente
        }

        assertNotNull(client);  // Verifica que el cliente fue creado correctamente

        // Crear una nueva cuenta para el cliente
        accountService.createAccountForCurrentClient(client);

        // Obtener las cuentas del cliente
        List<AccountDTO> clientAccounts = accountService.getClientAccounts(client);
        assertNotNull(clientAccounts);  // Verifica que las cuentas no sean nulas
        assertEquals(1, clientAccounts.size());  // Verifica que el cliente tenga una cuenta
    }


    // Test para obtener el cliente autenticado
    @Test
    @Transactional
    public void testGetAuthenticatedClient() {
        // Crear cliente si no existe
        Client client = clientService.findByEmail("testuser@example.com");
        if (client == null) {
            client = new Client("Test", "User", "testuser@example.com", "password");
            clientRepository.saveAndFlush(client);  // Asegúrate de guardar el cliente
        }

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testuser@example.com");

        Client authenticatedClient = accountService.getAuthenticatedClient(authentication);
        assertNotNull(authenticatedClient);
        assertEquals("testuser@example.com", authenticatedClient.getEmail());  // Verifica que el cliente autenticado es correcto
    }
}
