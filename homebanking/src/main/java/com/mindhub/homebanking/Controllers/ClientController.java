package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.DTOs.ClientDTO1;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @RequestMapping("/api/clients")
    public List<ClientDTO1> getClients(){
        return clientRepository.findAll().stream().map(client -> new ClientDTO1(client))
                                .collect(Collectors.toList());
    }

    @RequestMapping("api/clients/{id}")
    public ClientDTO1 getClient(@PathVariable Long id){
        return clientRepository.findById(id).map(client -> new ClientDTO1(client)).orElse(null);
    }

    @RequestMapping("/api/clients/current")
    public ClientDTO1 getClient(Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        ClientDTO1 clientDTO = new ClientDTO1(client);
        return clientDTO;
    }

    @RequestMapping(path = "/api/clients", method = RequestMethod.POST)

    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientRepository.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        clientRepository.save(new Client(firstName, lastName, email, passwordEncoder.encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);

    }
}
