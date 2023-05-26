package com.mindhub.homebanking.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    private Long id;
    private String number;
    private double balance;
    private LocalDateTime creationDate;
    private boolean status;

    @Enumerated(EnumType.STRING)
    private TypeAccount accountType;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @OneToMany(mappedBy="account", fetch = FetchType.EAGER)
    Set<Transaction> transactions = new HashSet<>();

    public Account(String number, double balance, LocalDateTime creationDate, TypeAccount accountType) {
        this.number = number;
        this.balance = balance;
        this.creationDate = creationDate;
        this.status = true;
        this.accountType = accountType;
    }

    public Account(String number, LocalDateTime creationDate) {
        this.number = number;
        this.balance = 0;
        this.creationDate = creationDate;
        this.status = true;
    }

    public Account() {}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @JsonIgnore
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction){
        transaction.setAccount(this);
        transactions.add(transaction);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public TypeAccount getAccountType() {
        return accountType;
    }

    public void setAccountType(TypeAccount accountType) {
        this.accountType = accountType;
    }
}
