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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name="loan_id")
    private Loan loan;


    public ClientLoan(){}

    public ClientLoan(double amount, int payments, String tipoLoan) {
        this.amount = amount;
        this.payments = payments;
        this.tipoLoan = tipoLoan;
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
}