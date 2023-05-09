package com.mindhub.homebanking.Service.implement;

import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Models.Transaction;
import com.mindhub.homebanking.Models.TypeTransaction;
import com.mindhub.homebanking.Repositories.AccountRepository;
import com.mindhub.homebanking.Repositories.ClientRepository;
import com.mindhub.homebanking.Repositories.TransactionRepository;
import com.mindhub.homebanking.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Service
public class TransactionServiceImplements implements TransactionService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void SaveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public void SaveAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public Set<Transaction> findAccountsByAccountAndDateBetween(Account account, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findAccountsByAccountAndDateBetween(account,startDate,endDate);
    }

    @Override
    public Client findEmailId(Authentication authentication){
        return clientRepository.findByEmail(authentication.getName());
    }

    @Override
    public Account findAccountByAccountNumber(String numberAccount){
        return accountRepository.findByNumber(numberAccount);
    }

}
