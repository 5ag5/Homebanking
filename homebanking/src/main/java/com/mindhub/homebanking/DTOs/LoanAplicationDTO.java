package com.mindhub.homebanking.DTOs;

import com.mindhub.homebanking.Models.ClientLoan;

public class LoanAplicationDTO {
    private long id;
    private double amount;
    private int payments;
    private String tipoLoan;
    private String accountDestination;

    private double interest;

    public LoanAplicationDTO(){}

    public LoanAplicationDTO(double amount, int payments, String tipoLoan, double interest, String accountDestination){
        this.amount = amount;
        this.payments = payments;
        this.tipoLoan = tipoLoan;
        this.interest = interest;
        this.accountDestination = accountDestination;
    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public String getTipoLoan() {
        return tipoLoan;
    }

    public String getAccountDestination() {
        return accountDestination;
    }

    public double getInterest() {
        return interest;
    }
}
