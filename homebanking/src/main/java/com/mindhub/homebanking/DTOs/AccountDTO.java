package com.mindhub.homebanking.DTOs;

import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Transaction;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {

    private Long id;
    private String number;
    private double balance;
    private LocalDateTime creationDate;

    private Set<TransactionDTO> transactions;

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.balance = account.getBalance();
        this.creationDate = account.getCreationDate();
        this.transactions = account.getTransactions()
                .stream()
                .map(transaction -> new TransactionDTO(transaction))
                .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }


    public String getNumber() {
        return number;
    }


    public double getBalance() {
        return balance;
    }


    public LocalDateTime getCreationDate() {
        return creationDate;
    }

}
