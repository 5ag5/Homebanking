package com.mindhub.homebanking.DTOs;

import com.mindhub.homebanking.Models.CardColor;
import com.mindhub.homebanking.Models.Card;
import com.mindhub.homebanking.Models.CardType;
import org.hibernate.tool.schema.internal.exec.ScriptSourceInputFromReader;

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

    private boolean status;

    public CardDTO(){
    }

    public CardDTO(Card card) {
        this.id = card.getId();
        this.cardHolder = card.getCardHolder();
        this.type = card.getType();
        this.cardColor = card.getColor();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.thruDate = card.getThruDate();
        this.fromDate = card.getFromDate();
        this.status = card.isStatus();
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

    public boolean isStatus() {
        return status;
    }
}
