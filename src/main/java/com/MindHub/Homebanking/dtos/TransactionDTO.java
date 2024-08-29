package com.MindHub.Homebanking.dtos;

import com.MindHub.Homebanking.models.Transaction;
import com.MindHub.Homebanking.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {
    private Long id;
    private String  description;
    private double amount;
    private LocalDateTime date;

    private TransactionType type;

    public TransactionDTO(Transaction transaction){
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.type = transaction.getType();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
