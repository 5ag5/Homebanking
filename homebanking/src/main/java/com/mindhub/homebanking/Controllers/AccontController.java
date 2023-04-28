package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.DTOs.AccountDTO;
import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Repositories.AccountRepository;
import com.mindhub.homebanking.Repositories.ClientRepository;
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
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;
    @RequestMapping("/api/accounts")
        public List<AccountDTO> getAccounts(){
            return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
        }

    @RequestMapping("/api/accounts/{id}")
        public AccountDTO getAccount(@PathVariable Long id){
        return accountRepository.findById(id).map(account -> new AccountDTO(account)).orElse(null);
    }

    @RequestMapping(path ="/api/clients/current/accounts", method= RequestMethod.POST )
    public ResponseEntity<Object> createAccount(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        int sizeSet = client.getAccounts().size();

        if (sizeSet > 2) {
            return new ResponseEntity<>("Reached above limit", HttpStatus.FORBIDDEN);
        } else {
            int min = 5;
            int max = 999999;

            String number = "VIN" + Math.round((Math.random() * (max - min) + min));
            double balance = 0;
            LocalDateTime creationDate = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String date1Tem = creationDate.format(formatter);
            LocalDateTime date1f = LocalDateTime.parse(date1Tem, formatter);

            Account account = new Account(number, balance, date1f);

            client.addAccount(account);
            accountRepository.save(account);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

}

