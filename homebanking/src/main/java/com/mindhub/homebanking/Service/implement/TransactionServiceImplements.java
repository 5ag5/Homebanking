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
    public Client findEmailId(Authentication authentication){
        return clientRepository.findByEmail(authentication.getName());
    }

    @Override
    public Account findAccountByAccountNumber(String numberAccount){
        return accountRepository.findByNumber(numberAccount);
    }

    @Override
    public ResponseEntity<Object> transactionTransfer(Authentication authentication, double amount, String description, String numberOrigin, String numberDestination) {

        Client client = findEmailId(authentication);
        Account accountOrigin = findAccountByAccountNumber(numberOrigin);
        Account accountDestination = findAccountByAccountNumber(numberDestination);

        if(description.isBlank() || amount == 0 || description.isBlank() ||
                numberOrigin.isBlank() || numberDestination.isBlank()){
            return new ResponseEntity<>("Data Missing", HttpStatus.FORBIDDEN);
        }

        if(numberOrigin.equals(numberDestination)){
            return new ResponseEntity<>("Numbers are the same, verify", HttpStatus.FORBIDDEN);
        }

        if(findAccountByAccountNumber(numberOrigin) == null){
            return new ResponseEntity<>("Number of account doesn't excist", HttpStatus.FORBIDDEN);
        }

        if (clientAccountQuery(client, numberOrigin) == false){
            return new ResponseEntity<>("Account Origin, doesn't belong to client", HttpStatus.FORBIDDEN);
        }

        if(findAccountByAccountNumber(numberDestination) == null){
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

        /*transactionRepository.save(transactionOrigin);
        transactionRepository.save(transactionDestination);

        accountRepository.save(accountOrigin);
        accountRepository.save(accountDestination);*/

        SaveTransaction(transactionOrigin);
        SaveTransaction(transactionDestination);

        SaveAccount(accountOrigin);
        SaveAccount(accountDestination);

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
