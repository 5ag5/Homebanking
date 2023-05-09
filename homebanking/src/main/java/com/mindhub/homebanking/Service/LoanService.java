package com.mindhub.homebanking.Service;

import com.mindhub.homebanking.DTOs.LoanAplicationDTO;
import com.mindhub.homebanking.DTOs.LoanDTO;
import com.mindhub.homebanking.DTOs.NewLoanDTO;
import com.mindhub.homebanking.Models.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface LoanService {

    void SaveClientLoan(ClientLoan clientLoan);

    void SaveTransaction(Transaction transaction);

    void SaveAccount(Account account);

    void SaveLoan(Loan loan);

    Client findCLientById(Authentication authentication);

    Loan findLoanByName(LoanAplicationDTO loanAplication);

    Loan findLoanTypeByName(String type);

    Account findAccountByNunber(LoanAplicationDTO loanAplication);

    //ResponseEntity<Object> loanRequest(Authentication authentication, LoanAplicationDTO loanAplication);

    List<LoanDTO> getAvailableLoans();

    //ResponseEntity<Object> createNewTypeOfLoan(NewLoanDTO newLoanDTO);

    ClientLoan findBySerialNumber(String serialNumber);

    Client findByName(String name);

    double queryInterestRate(String name);
}
