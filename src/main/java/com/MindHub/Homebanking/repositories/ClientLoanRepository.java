package com.MindHub.Homebanking.repositories;

import com.MindHub.Homebanking.models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientLoanRepository extends JpaRepository<ClientLoan, Long> {
}
