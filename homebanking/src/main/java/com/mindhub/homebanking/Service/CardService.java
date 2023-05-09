package com.mindhub.homebanking.Service;

import com.mindhub.homebanking.DTOs.CardDTO;
import com.mindhub.homebanking.Models.Card;
import com.mindhub.homebanking.Models.CardColor;
import com.mindhub.homebanking.Models.CardType;
import com.mindhub.homebanking.Models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CardService {

    void SaveCard(Card card);
    List<CardDTO> getCards();

    //ResponseEntity<Object> createCard(Authentication authentication,
    //                                       CardType cardType, CardColor cardColor);

    Client findEmailId(Authentication authentication);

    List<CardDTO> activeCards();

    //ResponseEntity<Object> modifyCardStatus(String number);

    Card findByNumber(String number);
}