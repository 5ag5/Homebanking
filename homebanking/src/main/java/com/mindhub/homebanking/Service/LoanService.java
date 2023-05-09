package com.mindhub.homebanking.Service;

import com.mindhub.homebanking.DTOs.LoanAplicationDTO;
import com.mindhub.homebanking.DTOs.LoanDTO;
import com.mindhub.homebanking.Models.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface LoanService {

    void SaveClientLoan(ClientLoan clientLoan);

    void SaveTransaction(Transaction transaction);

    void SaveAccount(Account account);

    void SaveLoan(Loan loan);

    Client findCLientById(Authentication authentication);

    Loan findLoanByName(LoanAplicationDTO loanAplication);

    Account findAccountByNunber(LoanAplicationDTO loanAplication);

    public ResponseEntity<Object> loanRequest(Authentication authentication, LoanAplicationDTO loanAplication);

    public List<LoanDTO> getAvailableLoans();
}
