package com.mindhub.homebanking.Service;

import com.mindhub.homebanking.DTOs.CardDTO;
import com.mindhub.homebanking.Models.Card;
import com.mindhub.homebanking.Models.CardColor;
import com.mindhub.homebanking.Models.CardType;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Repositories.CardRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

public interface CardService {

    void SaveCard(Card card);
    public List<CardDTO> getCards();

    public ResponseEntity<Object> createCard(Authentication authentication,
                                             CardType cardType, CardColor cardColor);

    public Client findEmailId(Authentication authentication);

}