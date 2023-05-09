package com.mindhub.homebanking.Service;

import com.mindhub.homebanking.DTOs.ClientDTO;
import com.mindhub.homebanking.Models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ClientService {

    void SaveClient(Client client);

    public List<ClientDTO> getClients();

    public ClientDTO getClient(@PathVariable Long id);

    public ClientDTO getClient(Authentication authentication);

    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password);

}
