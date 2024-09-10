package com.MindHub.Homebanking.repositories;

import com.MindHub.Homebanking.models.Card;
import com.MindHub.Homebanking.models.CardType;
import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.models.ColorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByClientAndColorAndType(Client client, ColorType color, CardType type);
}
