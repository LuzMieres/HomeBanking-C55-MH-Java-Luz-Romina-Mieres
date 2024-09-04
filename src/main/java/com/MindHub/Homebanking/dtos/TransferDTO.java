package com.MindHub.Homebanking.dtos;

public class TransferDTO {
    private double amount;
    private String description;
    private String originAccountNumber;
    private String destinationAccountNumber;

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getOriginAccountNumber() {
        return originAccountNumber;
    }

    public String getDestinationAccountNumber() {
        return destinationAccountNumber;
    }
}
