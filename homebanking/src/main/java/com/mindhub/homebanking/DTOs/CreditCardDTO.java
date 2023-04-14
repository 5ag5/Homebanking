package com.mindhub.homebanking.DTOs;

import com.mindhub.homebanking.Models.Color;
import com.mindhub.homebanking.Models.CreditCard;
import com.mindhub.homebanking.Models.TypeCard;

import java.time.LocalDate;

public class CreditCardDTO {
    private long id;
    private String cardHolder;
    private TypeCard type;
    private Color color;
    private String number;
    private int cvv;
    private LocalDate thruDate;
    private LocalDate fromDate;

    public CreditCardDTO(){
    }

    public CreditCardDTO(CreditCard creditcard) {
        this.id = creditcard.getId();
        this.cardHolder = creditcard.getCardHolder();;
        this.type = creditcard.getType();;
        this.color = creditcard.getColor();;
        this.number = creditcard.getNumber();;
        this.cvv = creditcard.getCvv();;
        this.thruDate = creditcard.getThruDate();;
        this.fromDate = creditcard.getFromDate();;
    }

    public long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public TypeCard getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    public String getNumber() {
        return number;
    }

    public int getCvv() {
        return cvv;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }
}
