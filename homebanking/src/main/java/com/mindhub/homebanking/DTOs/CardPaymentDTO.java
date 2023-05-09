package com.mindhub.homebanking.DTOs;

public class CardPaymentDTO {
    private String number;
    private int cvv;
    private double amount;
    private String description;

    //CONSTRUCTOR
    public CardPaymentDTO(){}

    //GETTER METHODS
    public String getNumber() {return number;}
    public int getCvv() {return cvv;}
    public double getAmount() {return amount;}
    public String getDescription() {return description;}
}
