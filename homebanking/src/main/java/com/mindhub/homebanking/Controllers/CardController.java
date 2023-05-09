package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.DTOs.CardDTO;
import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.Repositories.CardRepository;
import com.mindhub.homebanking.Repositories.ClientRepository;
import com.mindhub.homebanking.Service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class CardController {
    @Autowired
    CardService cardService;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    ClientRepository clientRepository;

    @RequestMapping("/api/cards")
    public List<CardDTO> getCards(){
        return cardService.getCards();
    }

    @RequestMapping(path= "/api/clients/current/cards", method= RequestMethod.POST)
    public ResponseEntity<Object> createCard(Authentication authentication, @RequestParam CardType cardType,
                           @RequestParam CardColor cardColor){
        return cardService.createCard(authentication,cardType,cardColor);
    }
}
