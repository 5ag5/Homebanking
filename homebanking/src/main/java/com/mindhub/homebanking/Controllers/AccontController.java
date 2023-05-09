package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.DTOs.AccountDTO;
import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Repositories.AccountRepository;
import com.mindhub.homebanking.Repositories.ClientRepository;
import com.mindhub.homebanking.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AccontController {

    @Autowired
    AccountService accountService;

    @RequestMapping("/api/accounts")
        public List<AccountDTO> getAccounts(){
            //return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
            return accountService.getAccounts();
        }

    @RequestMapping("/api/accounts/{id}")
        public AccountDTO getAccount(@PathVariable Long id){
        //return accountRepository.findById(id).map(account -> new AccountDTO(account)).orElse(null);
        return accountService.getAccount(id);
    }

    @RequestMapping(path ="/api/clients/current/accounts", method= RequestMethod.POST )
    public ResponseEntity<Object> createAccount(Authentication authentication) {
        return accountService.createAccount(authentication);
        }
    }

