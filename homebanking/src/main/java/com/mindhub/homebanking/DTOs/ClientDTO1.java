package com.mindhub.homebanking.DTOs;

import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Models.ClientLoan;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClientDTO1 {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<AccountDTO> accounts;
    private Set<ClientLoanDTO> loans;
    private Set<CreditCardDTO> creditCards;

    public ClientDTO1(Client client){
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
        this.loans = client.getClientLoans().stream().map(loan -> new ClientLoanDTO(loan)).collect(Collectors.toSet());
        this.creditCards = client.getCreditCards().stream().map(creditCard -> new CreditCardDTO(creditCard)).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }


    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getClientLoans() {
        return loans;
    }

    public Set<CreditCardDTO> getCreditCards() {
        return creditCards;
    }

}
