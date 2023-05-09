package com.mindhub.homebanking.DTOs;

import com.mindhub.homebanking.Models.ClientLoan;

public class ClientLoanDTO {
    private long id;
private String name;

private String serialNumber;
private double amount;
private int payments;
private boolean status;

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.name = clientLoan.getTipoLoan();
        this.serialNumber = clientLoan.getSerialNumber();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        this.status = clientLoan.isStatus();
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

    public boolean isStatus() {
        return status;
    }

    public String getSerialNumber() {
        return serialNumber;
    }
}
