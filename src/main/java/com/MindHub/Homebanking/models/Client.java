package com.MindHub.Homebanking.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String lastName;
    private String email;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Account>accounts = new HashSet<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<ClientLoan> clientLoans = new ArrayList<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<Card> cards = new ArrayList<>();

    public Client() { }

    public Client(String first, String last, String email) {
        this.firstName = first;
        this.lastName = last;
        this.email= email;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public long getId() {
        return id;
    }

    public Set<Account> getAccounts() {

        return accounts;
    }

    public List<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", accounts=" + accounts +
                ", clientLoans=" + clientLoans +
                ", cards=" + cards +
                '}';
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
        account.setClient(this);
    }

    public void addClientLoan(ClientLoan clientLoan) {
        this.clientLoans.add(clientLoan);
        clientLoan.setClient(this);
    }

    public void addCard(Card card){
        this.cards.add(card);
        card.setClient(this);
        card.setCardHolder(this.getFirstName() + " " + this.getLastName());
    }
}
