package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.DTOs.AccountDTO;
import com.mindhub.homebanking.DTOs.ClientDTO;
import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Models.TypeAccount;
import com.mindhub.homebanking.Repositories.AccountRepository;
import com.mindhub.homebanking.Repositories.ClientRepository;
import com.mindhub.homebanking.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class ClientController {
    private Client client;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ClientService clientService;

    @GetMapping("/api/clients")
    public List<ClientDTO> getClients(){
        return clientService.getClients();
    }

    @GetMapping("api/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientService.getClient(id);
    }

    //@RequestMapping("/api/clients/current")
    @GetMapping("/api/clients/current")
    public ClientDTO getClient(Authentication authentication){
        return clientService.getClient(authentication);
    }

    @PostMapping(path = "/api/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        //return clientService.register(firstName, lastName,email, password);

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientService.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        Client clientNew = new Client(firstName, lastName, email, passwordEncoder.encode(password));

        clientService.SaveClient(clientNew);

        int min = 5;
        int max = 999999;

        String number = "VIN" + Math.round((Math.random() * (max - min) + min));
        LocalDateTime creationDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String date1Tem = creationDate.format(formatter);
        LocalDateTime date1f = LocalDateTime.parse(date1Tem, formatter);

        Account account = new Account(number, 0, date1f, TypeAccount.SAVING);

        clientNew.addAccount(account);
        clientService.SaveAccount(account);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}



