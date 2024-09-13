package com.MindHub.Homebanking.repositories;

import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.models.ClientLoan;
import com.MindHub.Homebanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientLoanRepository extends JpaRepository<ClientLoan, Long> {
    Optional<Object> findByClientAndLoan(Client client, Loan loan);
}
