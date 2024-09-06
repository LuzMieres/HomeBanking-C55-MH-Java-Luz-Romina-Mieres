package com.MindHub.Homebanking.services;

import com.MindHub.Homebanking.dtos.LoanDTO;
import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.models.Loan;

import java.util.List;

public interface LoanService {
    List<Loan> getAllLoans();  // Método para obtener todos los préstamos
    List<LoanDTO> getAllLoanDTO();  // Método para obtener todos los préstamos como DTO
    Loan getLoanById(Long id);  // Método para obtener un préstamo por ID
    LoanDTO getLoanDTO(Loan loan);  // Método para convertir un préstamo a DTO
    void applyForLoan(String loanName, double amount, int payments, String destinationAccountNumber, Client client);  // Solicitar un préstamo basado en el nombre
}



