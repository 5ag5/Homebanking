package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.DTOs.ClientDTO1;
import com.mindhub.homebanking.Repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ClientController {

    ClientDTO1 VARIABLE;
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/api/clients")
    public List<ClientDTO1> getClients(){
        return clientRepository.findAll().stream().map(client -> new ClientDTO1(client))
                                .collect(Collectors.toList());
    }

    @RequestMapping("api/clients/{id}")

    public ClientDTO1 getClient(@PathVariable Long id){
        return clientRepository.findById(id).map(client -> new ClientDTO1(client)).orElse(null);
    }


}
