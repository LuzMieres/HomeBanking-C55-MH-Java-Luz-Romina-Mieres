package com.MindHub.Homebanking.models;

import jakarta.persistence.*;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amount;
    private int remainingPayments;  // Pagos restantes
    private int payments;  // Número total de pagos

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    public ClientLoan() {
    }

    public ClientLoan(double amount, int payments) {
        this.amount = amount;
        this.payments = payments;

    }

    public ClientLoan(double amount, int payments, Client client, Loan loan) {
        this.amount = amount;
        this.payments = payments;
        this.client = client;
        this.loan = loan;
    }

    public ClientLoan(String loanType, double amount, int payment) {
    }

    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public int getRemainingPayments() {
        return remainingPayments;
    }

    public void setRemainingPayments(int remainingPayments) {
        this.remainingPayments = remainingPayments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "ClientLoan{" +
                "id=" + id +
                ", amount=" + amount +
                ", payments=" + payments +
                '}';
    }
}
