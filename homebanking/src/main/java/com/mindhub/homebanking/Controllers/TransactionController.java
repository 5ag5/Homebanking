package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Models.Transaction;
import com.mindhub.homebanking.Models.TypeTransaction;
import com.mindhub.homebanking.Repositories.AccountRepository;
import com.mindhub.homebanking.Repositories.ClientRepository;
import com.mindhub.homebanking.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@RestController
public class TransactionController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Transactional
    @RequestMapping(path = "/api/transactions", method = RequestMethod.POST)
    public ResponseEntity<Object> transactionTransfer(
            Authentication authentication, @RequestParam double amount,
            @RequestParam String description, @RequestParam String numberOrigin,
            @RequestParam String numberDestination){

        Client client = clientRepository.findByEmail(authentication.getName());
        Account accountOrigin = accountRepository.findByNumber(numberOrigin);
        Account accountDestination = accountRepository.findByNumber(numberDestination);

        if(description.isBlank() || amount == 0 || description.isBlank() ||
            numberOrigin.isBlank() || numberDestination.isBlank()){
            return new ResponseEntity<>("Data Missing", HttpStatus.FORBIDDEN);
        }

        if(numberOrigin.equals(numberDestination)){
            return new ResponseEntity<>("Numbers are the same, verify", HttpStatus.FORBIDDEN);
        }

        if(accountRepository.findByNumber(numberOrigin) == null){
            return new ResponseEntity<>("Number of account doesn't excist", HttpStatus.FORBIDDEN);
        }

        if (clientAccountQuery(client, numberOrigin) == false){
            return new ResponseEntity<>("Account Origin, doesn't belong to client", HttpStatus.FORBIDDEN);
        }

        if(accountRepository.findByNumber(numberDestination) == null){
            return new ResponseEntity<>("Destination Account Doesn't exist",HttpStatus.FORBIDDEN);
        }

        if(accountAvailableFunds(accountOrigin, amount) == false){
            return new ResponseEntity<>("Insufficient funds",HttpStatus.FORBIDDEN);
        }

        Transaction transactionOrigin = new Transaction(TypeTransaction.DEBITO,-amount,
                description + " " + numberOrigin, timeTransaction());

        Transaction transactionDestination = new Transaction(TypeTransaction.CREDITO,amount,
                description + " " + numberDestination, timeTransaction());

        accountOrigin.setBalance(accountOrigin.getBalance()+transactionOrigin.getAmount());
        accountDestination.setBalance(accountDestination.getBalance()+transactionDestination.getAmount());

        accountOrigin.addTransaction(transactionOrigin);
        accountDestination.addTransaction(transactionDestination);

        transactionRepository.save(transactionOrigin);
        transactionRepository.save(transactionDestination);

        accountRepository.save(accountOrigin);
        accountRepository.save(accountDestination);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    private boolean clientAccountQuery(Client client, String numberOrigin) {
        Set<Account> tempSet = client.getAccounts();
        for(Account acct: tempSet){
            if(acct.getNumber().equals(numberOrigin)){
                return true;
            }
        }
        return false;
    }

    private boolean accountAvailableFunds(Account accountClient, Double amount){
        if(accountClient.getBalance()< amount){
            return false;
        }
        return true;
    }

    private LocalDateTime timeTransaction(){
        LocalDateTime creationDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String date1Tem = creationDate.format(formatter);
        return LocalDateTime.parse(date1Tem, formatter);
    }
}
