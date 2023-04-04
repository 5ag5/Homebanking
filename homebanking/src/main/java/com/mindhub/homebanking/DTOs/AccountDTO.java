package com.mindhub.homebanking.DTOs;

import com.mindhub.homebanking.Models.Account;

import java.time.LocalDateTime;

public class AccountDTO {

    private int id;
    private String number;
    private double balance;
    private LocalDateTime creationDate;

    public AccountDTO(Account account) {
        this.number = account.getNumber();
        this.balance = account.getBalance();
        this.creationDate = account.getCreationDate();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
