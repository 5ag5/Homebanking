package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.DTOs.AccountDTO;
import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Models.TypeAccount;
import com.mindhub.homebanking.Repositories.AccountRepository;
import com.mindhub.homebanking.Repositories.ClientRepository;
import com.mindhub.homebanking.Service.AccountService;
import com.mindhub.homebanking.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Access;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class AccontController {

    @Autowired
    AccountService accountService;

    @Autowired
    ClientService clientService;

    @GetMapping("/api/accounts")
        public List<AccountDTO> getAccounts(){
            return accountService.getAccounts();
        }

    @GetMapping("/api/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id, Authentication authentication){
        return accountService.getAccount(id);
    }

    @PostMapping(path ="/api/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication, @RequestParam TypeAccount accountType) {
        //return accountService.createAccount(authentication,accountType);

        Client client = clientService.findByEmail(authentication.getName());
        int sizeSet = client.getAccounts().stream().filter(account -> account.isStatus()).collect(Collectors.toSet()).size();

        if (sizeSet > 2) {
            return new ResponseEntity<>("Reached above limit of accounts allowed.", HttpStatus.FORBIDDEN);
        } else {
            int min = 5;
            int max = 999999;

            String number = "VIN" + Math.round((Math.random() * (max - min) + min));
            double balance = 0;
            LocalDateTime creationDate = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String date1Tem = creationDate.format(formatter);
            LocalDateTime date1f = LocalDateTime.parse(date1Tem, formatter);

            Account account = new Account(number, balance, date1f, accountType);

            client.addAccount(account);
            clientService.SaveAccount(account);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

    }

    @PostMapping(path = "/api/clients/current/accounts/delete")
    public ResponseEntity<Object> eliminateAccount (@RequestParam String account) {
        Account accountDel = accountService.findByNumber(account);
        accountDel.setStatus(false);
        accountService.SaveAccount(accountDel);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}

