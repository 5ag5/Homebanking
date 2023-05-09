package com.mindhub.homebanking.Service;

import com.mindhub.homebanking.DTOs.ClientDTO;
import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ClientService {

    void SaveClient(Client client);

    void SaveAccount(Account account);

    public List<ClientDTO> getClients();

    public ClientDTO getClient(Long id);

    public ClientDTO getClient(Authentication authentication);

    /*public ResponseEntity<Object> register(
             String firstName, String lastName,
             String email, String password);*/

    Client findByEmail(String email);

    Client findClient(Authentication authentication);
}
