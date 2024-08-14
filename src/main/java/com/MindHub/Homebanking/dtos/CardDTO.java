package com.MindHub.Homebanking.dtos;

import com.MindHub.Homebanking.models.Card;
import com.MindHub.Homebanking.models.CardType;
import com.MindHub.Homebanking.models.ColorType;

import java.time.LocalDate;


public class CardDTO {
    private Long id;
    private String cardholder;
    private CardType type;
    private ColorType color;
    private String number;
    private String cvv;
    private LocalDate fromDate;
    private LocalDate thruDate;

    public CardDTO(Card card){
        this.id = card.getId();
        this.cardholder = card.getCardholder();
        this.type = card.getType();
        this.color = card.getColor();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
    }

    public Long getId() {
        return id;
    }

    public String getCardholder() {
        return cardholder;
    }

    public CardType getType() {
        return type;
    }

    public ColorType getColor() {
        return color;
    }

    public String getNumber() {
        return number;
    }

    public String getCvv() {
        return cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }
}
