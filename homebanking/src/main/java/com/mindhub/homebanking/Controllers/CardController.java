package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.DTOs.CardDTO;
import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.Repositories.CardRepository;
import com.mindhub.homebanking.Repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
public class CardController {
    @Autowired
    CardRepository cardRepository;

    @Autowired
    ClientRepository clientRepository;

    @RequestMapping("/api/cards")
    public List<CardDTO> getCards(){
        return cardRepository.findAll().stream().map(Card -> new CardDTO(Card)).collect(Collectors.toList());
    }

    @RequestMapping(path= "/api/clients/current/cards", method= RequestMethod.POST)
    public ResponseEntity<Object> createCard(Authentication authentication, @RequestParam CardType cardType,
                           @RequestParam CardColor cardColor){
        Client client = clientRepository.findByEmail(authentication.getName());
        int sizeSet = client.getCards().size();

        if(cardType == null || cardColor == null){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if(sizeSet > 5){
            return new ResponseEntity<>("Client cannot order more than 6 cards", HttpStatus.FORBIDDEN);
        }else {
            String numeroF = createRandomNumber() + "-" + createRandomNumber() + "-" + createRandomNumber() + "-" + createRandomNumber();

            LocalDate date1 = LocalDate.now();

            int cvv = (int) Math.round(Math.random() * (999 - 100) + 100);

            Card newCard = new Card(client.getFirstName(), client.getLastName(), cardType, cardColor,
                    numeroF, cvv, date1.plusYears(5), date1);

            client.addCard(newCard);
            cardRepository.save(newCard);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    private int createRandomNumber(){
        return (int) (1000+ Math.random() * 9999);
    }



}
