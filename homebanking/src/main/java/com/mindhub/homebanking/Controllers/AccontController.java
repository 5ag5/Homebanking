package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.DTOs.AccountDTO;
import com.mindhub.homebanking.Repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AccontController {

    @Autowired
    private AccountRepository accountRepository;
    @RequestMapping("/api/accounts")
        public List<AccountDTO> getAccounts(){
            return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
        }

    @RequestMapping("/api/accounts/{id}")
        public AccountDTO getAccount(@PathVariable Long id){
        return accountRepository.findById(id).map(account -> new AccountDTO(account)).orElse(null);
    }

}

