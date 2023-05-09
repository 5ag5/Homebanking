package com.mindhub.homebanking.Service.implement;


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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplements implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void SaveAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public Client findEmailId(Authentication authentication) {
        return clientRepository.findByEmail(authentication.getName());
    }

    @Override
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }

    @Override
    public AccountDTO getAccount(Long id) {
        return accountRepository.findById(id).map(account -> new AccountDTO(account)).orElse(null);
    }

    @Override
    public ResponseEntity<Object> createAccount(Authentication authentication) {
        Client client = findEmailId(authentication);
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
            SaveAccount(account);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}
