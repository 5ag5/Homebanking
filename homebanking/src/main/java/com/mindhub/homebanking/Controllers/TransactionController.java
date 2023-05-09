package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.DTOs.ClientDTO;
import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Models.Transaction;
import com.mindhub.homebanking.Models.TypeTransaction;
import com.mindhub.homebanking.Repositories.AccountRepository;
import com.mindhub.homebanking.Repositories.ClientRepository;
import com.mindhub.homebanking.Repositories.TransactionRepository;
import com.mindhub.homebanking.Service.AccountService;
import com.mindhub.homebanking.Service.ClientService;
import com.mindhub.homebanking.Service.TransactionService;
import com.mindhub.homebanking.Service.TransactionsPDFExporter;
import com.mindhub.homebanking.Utils.TransactionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    ClientService clientServices;

    @Autowired
    AccountService accountServices;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    //@RequestMapping(path = "/api/transactions", method = RequestMethod.POST)
    @Transactional
    @PostMapping(path = "/api/transactions")
    public ResponseEntity<Object> transactionTransfer(
            Authentication authentication, @RequestParam double amount,
            @RequestParam String description, @RequestParam String numberOrigin,
            @RequestParam String numberDestination){

        //return transactionService.transactionTransfer(authentication, amount, description, numberOrigin, numberDestination);

        Client client = clientServices.findClient(authentication);
        Account accountOrigin = accountServices.getAccount(numberOrigin);
        Account accountDestination = accountServices.getAccount(numberDestination);

        if(description.isBlank() || amount == 0 || description.isBlank() ||
                numberOrigin.isBlank() || numberDestination.isBlank()){
            return new ResponseEntity<>("Data Missing", HttpStatus.FORBIDDEN);
        }

        if(numberOrigin.equals(numberDestination)){
            return new ResponseEntity<>("Numbers are the same, verify", HttpStatus.FORBIDDEN);
        }

        if(accountServices.getAccount(numberOrigin) == null){
            return new ResponseEntity<>("Number of account doesn't excist", HttpStatus.FORBIDDEN);
        }

        if (TransactionUtils.clientAccountQuery(client, numberOrigin) == false){
            return new ResponseEntity<>("Account Origin, doesn't belong to client", HttpStatus.FORBIDDEN);
        }

        if(accountServices.getAccount(numberDestination) == null){
            return new ResponseEntity<>("Destination Account Doesn't exist",HttpStatus.FORBIDDEN);
        }

        if(TransactionUtils.accountAvailableFunds(accountOrigin, amount) == false){
            return new ResponseEntity<>("Insufficient funds",HttpStatus.FORBIDDEN);
        }

        Transaction transactionOrigin = new Transaction(TypeTransaction.DEBITO,-amount,
                description + " " + numberOrigin, TransactionUtils.timeTransaction(),accountOrigin.getBalance()-amount);

        Transaction transactionDestination = new Transaction(TypeTransaction.CREDITO,amount,
                description + " " + numberDestination, TransactionUtils.timeTransaction(),accountDestination.getBalance()+amount);

        accountOrigin.setBalance(accountOrigin.getBalance()+transactionOrigin.getAmount());
        accountDestination.setBalance(accountDestination.getBalance()+transactionDestination.getAmount());

        accountOrigin.addTransaction(transactionOrigin);
        accountDestination.addTransaction(transactionDestination);

        /*transactionRepository.save(transactionOrigin);
        transactionRepository.save(transactionDestination);

        accountRepository.save(accountOrigin);
        accountRepository.save(accountDestination);*/

        transactionService.SaveTransaction(transactionOrigin);
        transactionService.SaveTransaction(transactionDestination);

        accountServices.SaveAccount(accountOrigin);
        accountServices.SaveAccount(accountDestination);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping(path="/api/transactions/transactionslist")
    public void exportToPDF(HttpServletResponse response,
                            @RequestParam String accountNumber,
                            @RequestParam String startDate, @RequestParam String endDate) throws IOException {
        response.setContentType("application/pdf");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=transactions_.pdf";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime1 = LocalDateTime.parse(startDate + " 00:00", formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(endDate + " 23:59", formatter);

        response.setHeader(headerKey,headerValue);

        Account account = accountServices.getAccount(accountNumber);

        Set<Transaction> setTransactions = transactionService.findAccountsByAccountAndDateBetween(account,dateTime1,dateTime2);
        //Set<Transaction> setTransactions = transactionRepository.findAccountsByAccountAndDateBetween(account,dateTime1,dateTime2);

        TransactionsPDFExporter exporter = new TransactionsPDFExporter(setTransactions, account);
        exporter.export(response);
    }
}
