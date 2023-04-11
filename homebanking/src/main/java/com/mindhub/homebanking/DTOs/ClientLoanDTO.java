package com.mindhub.homebanking.DTOs;

import com.mindhub.homebanking.Models.ClientLoan;

public class ClientLoanDTO {

private long id;

private String name;

private double amount;

private int payments;

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.name = clientLoan.getTipoLoan();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
    }

    public ClientLoanDTO(){}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }
}
