package com.MindHub.Homebanking.repositories;

import com.MindHub.Homebanking.models.Account;
import com.MindHub.Homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT COALESCE(MAX(CAST(SUBSTRING(a.number, 4, LENGTH(a.number)) AS int)), 0) FROM Account a WHERE a.number LIKE ?1%")
    int findMaxAccountNumberByPrefix(String prefix);

    List<Account> findByClient(Client client);
}
