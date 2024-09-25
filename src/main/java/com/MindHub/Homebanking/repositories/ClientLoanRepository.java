package com.MindHub.Homebanking.repositories;

import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.models.ClientLoan;
import com.MindHub.Homebanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientLoanRepository extends JpaRepository<ClientLoan, Long> {
    // Buscar préstamos por cliente
    List<ClientLoan> findByClient(Client client);

    // Buscar relación préstamo-cliente específica
    Optional<ClientLoan> findByLoanIdAndClient(Long loanId, Client client);

    Optional<Object> findByClientAndLoan(Client client, Loan loan);
}
