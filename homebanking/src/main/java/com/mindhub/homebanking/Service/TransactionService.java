package com.mindhub.homebanking.Service;

import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Models.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public interface TransactionService {

    Client findEmailId(Authentication authentication);

    abstract Account findAccountByAccountNumber(String numberAccount);

    /*public ResponseEntity<Object> transactionTransfer(
            Authentication authentication, double amount,
             String description, String numberOrigin,
             String numberDestination);*/

    void SaveTransaction(Transaction transaction);

    void SaveAccount(Account account);

    Set<Transaction> findAccountsByAccountAndDateBetween(Account account, LocalDateTime startDate, LocalDateTime endDate);

}
