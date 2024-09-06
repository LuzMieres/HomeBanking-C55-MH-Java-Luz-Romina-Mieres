package com.MindHub.Homebanking.repositories;

import com.MindHub.Homebanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Loan findByName(String loanName);
}



