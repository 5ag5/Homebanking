package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.DTOs.CardDTO;
import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.Repositories.CardRepository;
import com.mindhub.homebanking.Repositories.ClientRepository;
import com.mindhub.homebanking.Service.CardService;
import com.mindhub.homebanking.Service.ClientService;
import com.mindhub.homebanking.Utils.CardUtils;
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
    ClientService clientService;

    @GetMapping("/api/cards")
    public List<CardDTO> getActiveCards(){
        return cardService.activeCards();
    }

    @PostMapping(path= "/api/clients/current/cards")
    public ResponseEntity<Object> createCard(Authentication authentication, @RequestParam CardType cardType,
                           @RequestParam CardColor cardColor){
        //return cardService.createCard(authentication,cardType,cardColor);

        Client client = clientService.findClient(authentication);
        Set<Card> clientCards = client.getCards();

        if(cardType == null || cardColor == null){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if(CardUtils.numberOfCreditCards(clientCards, cardType)){
            return new ResponseEntity<>("Client cannot order more than 3 credit cards", HttpStatus.FORBIDDEN);
        }

        if(CardUtils.numberOfDebitCards(clientCards, cardType)){
            return new ResponseEntity<>("Client cannot order more than 3 debit cards", HttpStatus.FORBIDDEN);
        }

        String numeroF = CardUtils.getCardNumber();

        LocalDate date1 = LocalDate.now();

        int cvv = CardUtils.getCVV();

        Card newCard = new Card(client.getFirstName(), client.getLastName(), cardType, cardColor,
                numeroF, cvv, date1.plusYears(5), date1);

        client.addCard(newCard);
        cardService.SaveCard(newCard);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PostMapping(path="/api/clients/current/cards/delete")
    public ResponseEntity<Object> modifyCardStatus(@RequestParam String cardNumber){
        //return cardService.modifyCardStatus(cardNumber);
        Card card = cardService.findByNumber(cardNumber);
        card.setStatus(false);
        cardService.SaveCard(card);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
