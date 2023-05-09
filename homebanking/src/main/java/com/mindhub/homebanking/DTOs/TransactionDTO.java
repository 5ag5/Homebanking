package com.mindhub.homebanking.DTOs;

import com.mindhub.homebanking.Models.Transaction;
import com.mindhub.homebanking.Models.TypeTransaction;

import java.time.LocalDateTime;

public class TransactionDTO {

    private Long id;
    private TypeTransaction type;
    private double amount;
    private String description;
    private LocalDateTime date;
    private double currentBalance;
    private boolean status;

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
        this.status = transaction.isStatus();
        this.currentBalance = transaction.getCurrentBalance();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeTransaction getType() {
        return type;
    }

    public void setType(TypeTransaction type) {
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

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public boolean isStatus() {
        return status;
    }
}
