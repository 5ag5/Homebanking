package com.mindhub.homebanking.Service.implement;

import com.mindhub.homebanking.DTOs.ClientDTO;
import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.Repositories.AccountRepository;
import com.mindhub.homebanking.Repositories.ClientRepository;
import com.mindhub.homebanking.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClientServiceImplements implements ClientService {
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public void SaveClient(Client client) {
        clientRepository.save(client);
    }

    @Override
    public void SaveAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public List<ClientDTO> getClients() {
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client))
                .collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClient(Long id) {
        return clientRepository.findById(id).map(client -> new ClientDTO(client)).orElse(null);
    }

    @Override
    public ClientDTO getClient(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        Set<Card> AllCards = client.getCards().stream().filter(card -> card.isStatus()).collect(Collectors.toSet());
        Set<Account> AllAccounts = client.getAccounts().stream().filter(account -> account.isStatus()).collect(Collectors.toSet());
        Set<ClientLoan> AllLoans = client.getClientLoans().stream().filter(clientLoan -> clientLoan.isStatus()).collect(Collectors.toSet());
        client.setCreditCards(AllCards);
        client.setAccounts(AllAccounts);
        client.setClientLoans(AllLoans);
        return new ClientDTO(client);
    }

    /*@Override
    public ResponseEntity<Object> register(String firstName, String lastName, String email, String password) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientRepository.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        Client clientNew = new Client(firstName, lastName, email, passwordEncoder.encode(password));

        SaveClient(clientNew);

        int min = 5;
        int max = 999999;

        String number = "VIN" + Math.round((Math.random() * (max - min) + min));
        LocalDateTime creationDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String date1Tem = creationDate.format(formatter);
        LocalDateTime date1f = LocalDateTime.parse(date1Tem, formatter);

        Account account = new Account(number, 0, date1f, TypeAccount.SAVING);

        clientNew.addAccount(account);
        SaveAccount(account);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }*/

    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public Client findClient(Authentication authentication) {
        return clientRepository.findByEmail(authentication.getName());
    }
}
