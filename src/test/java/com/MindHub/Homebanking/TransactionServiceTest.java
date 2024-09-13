package com.MindHub.Homebanking;

import com.MindHub.Homebanking.dtos.TransferDTO;
import com.MindHub.Homebanking.models.Account;
import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.models.Transaction;
import com.MindHub.Homebanking.repositories.AccountRepository;
import com.MindHub.Homebanking.repositories.ClientRepository;
import com.MindHub.Homebanking.repositories.TransactionRepository;
import com.MindHub.Homebanking.services.AccountService;
import com.MindHub.Homebanking.services.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientRepository clientRepository;

    // Test para crear una transacción exitosa
    @Test
    @Transactional
    public void testCreateTransaction() {
        // Crear y guardar cliente
        Client client = new Client("John", "Doe", "john.doe@example.com", "password");
        clientRepository.save(client);  // Guardar el cliente en la base de datos

        // Crear cuentas y asociarlas al cliente
        Account originAccount = new Account("VIN001", LocalDate.now(), 1000);
        originAccount.setClient(client);  // Asociar la cuenta origen al cliente
        accountRepository.save(originAccount);  // Guardar la cuenta origen en la base de datos

        Account destinationAccount = new Account("VIN002", LocalDate.now(), 500);
        destinationAccount.setClient(client);  // Asociar la cuenta destino al cliente
        accountRepository.save(destinationAccount);  // Guardar la cuenta destino en la base de datos

        // Crear un TransferDTO para la transacción
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setAmount(200);
        transferDTO.setOriginAccountNumber("VIN001");
        transferDTO.setDestinationAccountNumber("VIN002");

        // Crear la transacción
        transactionService.createTransaction(transferDTO, client);

        // Verificar que las transacciones se hayan guardado correctamente
        List<Transaction> originTransactions = transactionRepository.findByAccountNumber("VIN001");
        List<Transaction> destinationTransactions = transactionRepository.findByAccountNumber("VIN002");

        assertNotNull(originTransactions);
        assertNotNull(destinationTransactions);
        assertEquals(1, originTransactions.size());  // Verifica que haya una transacción de débito
        assertEquals(1, destinationTransactions.size());  // Verifica que haya una transacción de crédito

        // Verificar que los balances se hayan actualizado correctamente
        Account updatedOriginAccount = accountRepository.findByNumber("VIN001").get(0);
        Account updatedDestinationAccount = accountRepository.findByNumber("VIN002").get(0);
        assertEquals(800, updatedOriginAccount.getBalance());  // El saldo de la cuenta origen debería haber disminuido
        assertEquals(700, updatedDestinationAccount.getBalance());  // El saldo de la cuenta destino debería haber aumentado
    }


    // Test para validar que no se pueda realizar una transacción sin saldo suficiente
    @Test
    @Transactional
    public void testInsufficientBalance() {
        // Crear cliente y guardar en la base de datos
        Client client = new Client("Jane", "Doe", "jane.doe@example.com", "password");
        clientRepository.save(client);

        // Crear cuenta origen y asociarla al cliente
        Account originAccount = new Account("VIN003", LocalDate.now(), 100);
        originAccount.setClient(client);  // Asocia la cuenta al cliente
        accountRepository.save(originAccount);  // Guardar la cuenta en la base de datos

        // Crear cuenta destino y guardarla en la base de datos
        Account destinationAccount = new Account("VIN004", LocalDate.now(), 500);
        accountRepository.save(destinationAccount);  // Guardar la cuenta en la base de datos

        // Crear el DTO para la transferencia
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setAmount(200);  // Monto superior al balance de la cuenta origen
        transferDTO.setOriginAccountNumber("VIN003");
        transferDTO.setDestinationAccountNumber("VIN004");

        // Intentar realizar la transacción
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.createTransaction(transferDTO, client);
        });

        assertEquals("Insufficient balance", exception.getMessage());  // Verifica el mensaje de la excepción
    }



    // Test para validar que la cuenta origen pertenezca al cliente autenticado
    @Test
    @Transactional
    public void testAccountOwnershipValidation() {
        // Crear cliente propietario de la cuenta origen
        Client client = new Client("John", "Doe", "john.doe@example.com", "password");
        clientRepository.save(client);

        // Crear cuenta origen y asociarla al cliente
        Account originAccount = new Account("VIN005", LocalDate.now(), 1000);
        originAccount.setClient(client);  // Asocia la cuenta al cliente
        accountRepository.save(originAccount);  // Guardar la cuenta en la base de datos

        // Crear cuenta destino y guardarla en la base de datos
        Account destinationAccount = new Account("VIN006", LocalDate.now(), 500);
        accountRepository.save(destinationAccount);  // Guardar la cuenta en la base de datos

        // Crear cliente diferente para simular la validación de propiedad de la cuenta
        Client anotherClient = new Client("Jane", "Doe", "jane.doe@example.com", "password");
        clientRepository.save(anotherClient);

        // Crear el DTO para la transferencia
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setAmount(200);
        transferDTO.setOriginAccountNumber("VIN005");
        transferDTO.setDestinationAccountNumber("VIN006");

        // Intentar realizar la transacción y validar que la cuenta origen no pertenece al cliente actual
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.createTransaction(transferDTO, anotherClient);
        });

        assertEquals("Origin account does not belong to the authenticated client", exception.getMessage());  // Verifica el mensaje de la excepción
    }


}

