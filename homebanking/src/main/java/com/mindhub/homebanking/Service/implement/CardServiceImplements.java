package com.mindhub.homebanking.Service.implement;

import com.mindhub.homebanking.DTOs.CardDTO;
import com.mindhub.homebanking.Models.Card;
import com.mindhub.homebanking.Models.CardColor;
import com.mindhub.homebanking.Models.CardType;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Repositories.CardRepository;
import com.mindhub.homebanking.Repositories.ClientRepository;
import com.mindhub.homebanking.Service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CardServiceImplements implements CardService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    ClientRepository clientRepository;

    @Override
    public void SaveCard(Card card) {
        cardRepository.save(card);
    }

    @Override
    public Client findEmailId(Authentication authentication) {
        return clientRepository.findByEmail(authentication.getName());
    }

    @Override
    public List<CardDTO> getCards() {
        return cardRepository.findAll().stream().map(Card -> new CardDTO(Card)).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<Object> createCard(Authentication authentication, CardType cardType, CardColor cardColor) {

        Client client = findEmailId(authentication);
        Set<Card> clientCards = client.getCards();
        int sizeSet = client.getCards().size();

        if(cardType == null || cardColor == null){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if(numberOfCreditCards(clientCards, cardType)){
            return new ResponseEntity<>("Client cannot order more than 3 credit cards", HttpStatus.FORBIDDEN);
        }

        if(numberOfDebitCards(clientCards, cardType)){
            return new ResponseEntity<>("Client cannot order more than 3 debit cards", HttpStatus.FORBIDDEN);
        }

        String numeroF = createRandomNumber() + "-" + createRandomNumber() + "-" + createRandomNumber() + "-" + createRandomNumber();

        LocalDate date1 = LocalDate.now();

        int cvv = (int) Math.round(Math.random() * (999 - 100) + 100);

        Card newCard = new Card(client.getFirstName(), client.getLastName(), cardType, cardColor,
                numeroF, cvv, date1.plusYears(5), date1);

        client.addCard(newCard);
        SaveCard(newCard);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



    public boolean numberOfCreditCards(Set<Card> clientCards, CardType cardType) {
        int countCredit =0;

        for (Card card:clientCards){
            if(card.getType() == CardType.CREDIT){
                countCredit = countCredit+1;
            }
        }

        if(cardType == CardType.CREDIT){
            countCredit = countCredit+ 1;
        }

        return countCredit > 3;
    }

    public boolean numberOfDebitCards(Set<Card> clientCards, CardType cardType) {
        int countDebit =0;

        for (Card card:clientCards){
            if(card.getType() == CardType.DEBIT){
                countDebit = countDebit+1;
            }
        }

        if(cardType == CardType.DEBIT){
            countDebit = countDebit +1;
        }

        return countDebit > 3;
    }

    public int createRandomNumber() {
        return (int) (1000+ Math.random() * 9999);
    }
}
