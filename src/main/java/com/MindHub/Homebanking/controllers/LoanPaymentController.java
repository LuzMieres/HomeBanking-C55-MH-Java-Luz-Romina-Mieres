package com.MindHub.Homebanking.controllers;

import com.MindHub.Homebanking.dtos.LoanPaymentDTO;
import com.MindHub.Homebanking.services.LoanPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
public class LoanPaymentController {

    @Autowired
    private LoanPaymentService loanPaymentService;

    @PostMapping("/pay")
    public ResponseEntity<?> payLoan(@AuthenticationPrincipal UserDetails userDetails,
                                     @RequestBody LoanPaymentDTO loanPaymentDTO) {
        try {
            loanPaymentService.payLoan(userDetails.getUsername(), loanPaymentDTO);
            return ResponseEntity.ok("Loan payment successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
