package com.MindHub.Homebanking.repositories;

import com.MindHub.Homebanking.models.Account;
import com.MindHub.Homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    // Método ya existente para encontrar el máximo número de cuenta por prefijo
    @Query("SELECT COALESCE(MAX(CAST(SUBSTRING(a.number, 4, LENGTH(a.number)) AS int)), 0) FROM Account a WHERE a.number LIKE ?1%")
    int findMaxAccountNumberByPrefix(String prefix);

    // Método ya existente para encontrar cuentas por cliente
    List<Account> findByClient(Client client);

    // Nuevo método para buscar una cuenta por su número
    Optional<Account> findByNumber(String number);
}
