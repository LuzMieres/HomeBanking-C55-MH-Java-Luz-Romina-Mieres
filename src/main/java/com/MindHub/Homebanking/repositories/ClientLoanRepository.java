package com.MindHub.Homebanking.repositories;

import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.models.ClientLoan;
import com.MindHub.Homebanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientLoanRepository extends JpaRepository<ClientLoan, Long> {
    @Query("SELECT cl FROM ClientLoan cl WHERE cl.client = :client AND cl.loan = :loan")
    Optional<ClientLoan> findByClientAndLoan(@Param("client") Client client, @Param("loan") Loan loan);

}
