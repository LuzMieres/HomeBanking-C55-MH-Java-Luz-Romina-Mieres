package com.MindHub.Homebanking;

import com.MindHub.Homebanking.dtos.LoanDTO;
import com.MindHub.Homebanking.models.Account;
import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.models.Loan;
import com.MindHub.Homebanking.models.ClientLoan;
import com.MindHub.Homebanking.repositories.AccountRepository;
import com.MindHub.Homebanking.repositories.ClientLoanRepository;
import com.MindHub.Homebanking.repositories.LoanRepository;
import com.MindHub.Homebanking.repositories.TransactionRepository;
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

//    // Test para aplicar un préstamo a un cliente
//    @Test
//    @Transactional
//    public void testApplyForLoan() {
//        Client client = new Client("John", "Doe", "john.doe@example.com", passwordEncoder.encode("123"));
//        Account account = new Account("VIN020", LocalDate.now(), 50000);
//        Loan loan = new Loan("Personal", 100000, List.of(12, 24, 36));
//
//        // Guardar el cliente, la cuenta y el préstamo en la base de datos
//        accountRepository.save(account);
//        loanRepository.save(loan);
//
//        // Aplicar el préstamo
//        loanService.applyForLoan("Personal", 50000, 12, "VIN020", client);
//
//        // Obtener las cuentas con el número "VIN001"
//        List<Account> updatedAccounts = accountRepository.findByNumber("VIN020");
//
//        Account updatedAccount;
//        if (updatedAccounts.isEmpty()) {
//            updatedAccount = null;  // Si no hay ninguna cuenta con ese número, la cuenta es nula
//        } else if (updatedAccounts.size() > 1) {
//            throw new IllegalArgumentException("Multiple accounts found with the same account number");
//        } else {
//            updatedAccount = updatedAccounts.get(0);  // Si hay una sola cuenta, la asignamos
//        }
//
//        assertNotNull(updatedAccount);  // Verifica que la cuenta de destino exista
//        assertEquals(100000, updatedAccount.getBalance());  // Verifica que el balance se haya actualizado correctamente
//
//        ClientLoan clientLoan = (ClientLoan) clientLoanRepository.findByClientAndLoan(client, loan).orElse(null);
//        assertNotNull(clientLoan);  // Verifica que la relación entre cliente y préstamo haya sido creada
//        assertEquals(50000, clientLoan.getAmount());  // Verifica que el monto del préstamo sea el correcto
//    }

}