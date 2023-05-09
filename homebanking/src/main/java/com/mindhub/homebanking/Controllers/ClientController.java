package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.DTOs.ClientDTO;
import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Client;
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
    ClientService clientService;

    @RequestMapping("/api/clients")
    public List<ClientDTO> getClients(){
        return clientService.getClients();
    }

    @RequestMapping("api/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientService.getClient(id);
    }

    @RequestMapping("/api/clients/current")
    public ClientDTO getClient(Authentication authentication){
        return clientService.getClient(authentication);
    }

    @RequestMapping(path = "/api/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        return clientService.register(firstName, lastName,email, password);

    }

}



