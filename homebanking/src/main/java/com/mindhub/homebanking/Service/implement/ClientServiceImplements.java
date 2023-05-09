package com.mindhub.homebanking.Service.implement;

import com.mindhub.homebanking.DTOs.ClientDTO;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Repositories.ClientRepository;
import com.mindhub.homebanking.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.stream.Collectors;

public class ClientServiceImplements implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public void SaveClient(Client client) {

    }
    @Override
    public List<ClientDTO> getClients() {
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client))
                .collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClient(Long id) {
        return null;
    }

    @Override
    public ClientDTO getClient(Authentication authentication) {
        return null;
    }

    @Override
    public ResponseEntity<Object> register(String firstName, String lastName, String email, String password) {
        return null;
    }
}
