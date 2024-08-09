package com.MindHub.Homebanking.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private double amount;
    private String description;
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_owner_id")
    private Account account;

    public Transaction() {
    }

    public Transaction(TransactionType type, double amount, String description, LocalDateTime date) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type=" + type +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
