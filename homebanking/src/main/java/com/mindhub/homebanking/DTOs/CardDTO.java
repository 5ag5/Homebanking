package com.mindhub.homebanking.DTOs;

import com.mindhub.homebanking.Models.CardColor;
import com.mindhub.homebanking.Models.Card;
import com.mindhub.homebanking.Models.CardType;

import java.time.LocalDate;

public class CardDTO {
    private long id;
    private String cardHolder;
    private CardType type;
    private CardColor cardColor;
    private String number;
    private int cvv;
    private LocalDate thruDate;
    private LocalDate fromDate;

    public CardDTO(){
    }

    public CardDTO(Card creditcard) {
        this.id = creditcard.getId();
        this.cardHolder = creditcard.getCardHolder();;
        this.type = creditcard.getType();;
        this.cardColor = creditcard.getColor();;
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

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return cardColor;
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
