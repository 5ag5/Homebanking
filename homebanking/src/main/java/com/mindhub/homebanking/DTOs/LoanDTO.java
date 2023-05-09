package com.mindhub.homebanking.DTOs;

import com.mindhub.homebanking.Models.Loan;
import com.mindhub.homebanking.Utils.LoanUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LoanDTO {
    private long id;
    private String name;
    private double MaxAmount;
    private List<Integer> payments;
    private Set<ClientLoanDTO> clientLoans;
    private double interest;
    private double [][] listInst;
    public LoanDTO(){}

    public LoanDTO(Loan loan){
        this.id = loan.getId();
        this.name = loan.getName();
        this.MaxAmount = loan.getMaxAmount();
        this.payments = loan.getPayments();
        this.clientLoans = loan.getClientLoans().stream().map(clientLoan -> new ClientLoanDTO(clientLoan)).collect(Collectors.toSet());
        this.interest = loan.getInterest();
        this.listInst = loan.getListInst();
    }
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMaxAmount() {
        return MaxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public Set<ClientLoanDTO> getClientLoans() {
        return clientLoans;
    }

    public double getInterest() {
        return interest;
    }

    public double [][] getListInst() {
        return listInst;
    }
}
