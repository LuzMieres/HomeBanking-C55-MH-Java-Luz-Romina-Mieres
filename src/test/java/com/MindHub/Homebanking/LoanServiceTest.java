package com.MindHub.Homebanking;

import com.MindHub.Homebanking.dtos.LoanDTO;
import com.MindHub.Homebanking.models.Account;
import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.models.Loan;
import com.MindHub.Homebanking.models.ClientLoan;
import com.MindHub.Homebanking.repositories.*;
import com.MindHub.Homebanking.services.LoanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LoanServiceTest {

    @Autowired
    private LoanService loanService;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    // Test para obtener todos los préstamos
    @Test
    @Transactional
    public void testGetAllLoans() {
        List<Loan> loans = loanService.getAllLoans();
        assertNotNull(loans);  // Verifica que la lista no sea nula
        assertTrue(loans.size() > 0);  // Verifica que haya al menos un préstamo en la base de datos
    }

    // Test para obtener todos los préstamos como DTO
    @Test
    @Transactional
    public void testGetAllLoanDTO() {
        List<LoanDTO> loanDTOs = loanService.getAllLoanDTO();
        assertNotNull(loanDTOs);  // Verifica que la lista de DTO no sea nula
        assertTrue(loanDTOs.size() > 0);  // Verifica que haya al menos un préstamo convertido a DTO
    }

    // Test para obtener un préstamo por ID
    @Test
    @Transactional
    public void testGetLoanById() {
        Loan loan = new Loan("Personal", 100000, List.of(12, 24, 36));
        loanRepository.save(loan);  // Guardar un préstamo en la base de datos

        Loan foundLoan = loanService.getLoanById(loan.getId());
        assertNotNull(foundLoan);  // Verifica que el préstamo se haya encontrado
        assertEquals(loan.getId(), foundLoan.getId());  // Verifica que el préstamo encontrado sea el correcto
    }

//    @Test
//    @Transactional
//    public void testApplyForLoan() {
//        // Crear un cliente y asociar una cuenta
//        Client client = new Client("John", "Doe", "john.doe@example.com", passwordEncoder.encode("123"));
//        Account account = new Account("VIN026", LocalDate.now(), 50000);
//        account.setClient(client);  // Asegurarse de que la cuenta esté asociada al cliente
//        client.addAccount(account); // Relación bidireccional
//
//        Loan loan = new Loan("Personal", 1000000, List.of(12, 24, 36, 48, 60, 72, 90));
//
//        // Guardar el cliente, la cuenta y el préstamo en la base de datos
//        clientRepository.save(client);
//        accountRepository.save(account);
//        loanRepository.save(loan);
//
//        // Aplicar el préstamo
//        loanService.applyForLoan("Personal", 50000, 12, "VIN026", client);
//
//        // Obtener las cuentas con el número "VIN026"
//        List<Account> updatedAccounts = accountRepository.findByNumber("VIN026");
//
//        Account updatedAccount = updatedAccounts.get(0);
//        assertNotNull(updatedAccount);  // Verifica que la cuenta de destino exista
//
//        // Verificar balance de cuenta
//        System.out.println("Expected Balance: 100000");
//        System.out.println("Actual Balance: " + updatedAccount.getBalance());
//        assertEquals(100000, updatedAccount.getBalance());
//
//        // Buscar el ClientLoan creado
//        System.out.println("Searching for ClientLoan...");
//        ClientLoan clientLoan = clientLoanRepository.findByClientAndLoan(client, loan).orElse(null);
//        System.out.println("ClientLoan found: " + clientLoan);  // Verifica si se encontró el clientLoan
//         // Verifica que la relación entre cliente y préstamo haya sido creada
//        assert clientLoan != null;
//        assertEquals(50000, clientLoan.getAmount());  // Verifica que el monto del préstamo sea el correcto
//    }





}

