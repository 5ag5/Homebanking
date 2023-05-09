package com.mindhub.homebanking.Service.implement;

import com.mindhub.homebanking.DTOs.LoanAplicationDTO;
import com.mindhub.homebanking.DTOs.LoanDTO;
import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.Repositories.*;
import com.mindhub.homebanking.Service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LoanServiceImplements implements LoanService {
    @Autowired
    ClientLoanRepository clientLoanRepository;

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    LoanRepository loanRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientRepository clientRepository;

    @Override
    public void SaveClientLoan(ClientLoan clientLoan) {
        clientLoanRepository.save(clientLoan);
    }

    @Override
    public void SaveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);

    }

    @Override
    public void SaveAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public void SaveLoan(Loan loan) {
        loanRepository.save(loan);
    }

    @Override
    public Client findCLientById(Authentication authentication){
        return clientRepository.findByEmail(authentication.getName());
    }

    @Override
    public Loan findLoanByName(LoanAplicationDTO loanAplication){
        return loanRepository.findByName(loanAplication.getTipoLoan());
    }

    @Override
    public Loan findLoanTypeByName(String type){
        return loanRepository.findByName(type);
    }

    @Override
    public Account findAccountByNunber(LoanAplicationDTO loanAplication){
        return accountRepository.findByNumber(loanAplication.getAccountDestination());
    }

    @Override
    public List<LoanDTO> getAvailableLoans() {
        return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }

    @Override
    public ClientLoan findBySerialNumber(String serialNumber) {
        return clientLoanRepository.findBySerialNumber(serialNumber);
    }


    @Override
    public Client findByName(String name) {
        return clientRepository.findByEmail(name);
    }


    /*private void verifyIfBalanceIs0(ClientLoan loan) {
        if(loan.getAmount() <= 0){
            loan.setStatus(false);
        }
        SaveClientLoan(loan);
    }*/

    @Override
    public double queryInterestRate(String name) {
        Loan loan = loanRepository.findByName(name);
        return loan.getInterest();
    }

}
