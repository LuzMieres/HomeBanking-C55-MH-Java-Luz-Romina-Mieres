package com.MindHub.Homebanking.services;

import com.MindHub.Homebanking.dtos.LoanPaymentDTO;

public interface LoanPaymentService {
    void payLoan(String username, LoanPaymentDTO loanPaymentDTO) throws Exception;
}
