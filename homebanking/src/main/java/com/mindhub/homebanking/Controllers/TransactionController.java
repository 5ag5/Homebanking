package com.mindhub.homebanking.Controllers;

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
    TransactionService transactionService;


    @Transactional
    @RequestMapping(path = "/api/transactions", method = RequestMethod.POST)
    public ResponseEntity<Object> transactionTransfer(
            Authentication authentication, @RequestParam double amount,
            @RequestParam String description, @RequestParam String numberOrigin,
            @RequestParam String numberDestination){

            return transactionService.transactionTransfer(authentication, amount, description, numberOrigin, numberDestination);
    }

}
