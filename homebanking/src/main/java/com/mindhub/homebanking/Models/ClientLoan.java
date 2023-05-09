package com.mindhub.homebanking.Models;

import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class ClientLoan {

    @Id
@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
@GenericGenerator(name = "native", strategy = "native")
    private long id;
    private double amount;
    private int payments;
    private String tipoLoan;
    private double interest;
    private String serialNumber;

    private boolean status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name="loan_id")
    private Loan loan;


    public ClientLoan(){}

    public ClientLoan(double amount, String serialNumber, int payments, String tipoLoan, double interest) {
        this.amount = amount;
        this.serialNumber = serialNumber;
        this.payments = payments;
        this.tipoLoan = tipoLoan;
        this.interest = interest;
        this.status = true;
    }

    public long getId() {
        return id;
    }

    public String getTipoLoan() {
        return tipoLoan;
    }

    public void setTipoLoan(String tipoLoan) {
        this.tipoLoan = tipoLoan;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getSerialNumber() {
        return serialNumber;
    }
}
