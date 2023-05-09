package com.mindhub.homebanking.DTOs;

import com.mindhub.homebanking.Utils.LoanUtils;

import java.util.List;

public class NewLoanDTO {

    private String name;
    private double MaxAmount;
    private List<Integer> payments;
    private double interest;
    private double [][] listInst;

    public NewLoanDTO(String name, double MaxAmount, List<Integer> payments, double interest) {
        this.name = name;
        this.MaxAmount = MaxAmount;
        this.payments = payments;
        this.interest = interest;
        this.listInst = LoanUtils.interestsPayments;
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

    public double getInterest() {
        return interest;
    }

    public double[][] getListInst() {
        return listInst;
    }
}
