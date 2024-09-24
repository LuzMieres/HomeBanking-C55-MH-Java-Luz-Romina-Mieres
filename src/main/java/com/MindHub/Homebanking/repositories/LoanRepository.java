package com.MindHub.Homebanking.repositories;

import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.models.ClientLoan;
import com.MindHub.Homebanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByName(String name);
}



