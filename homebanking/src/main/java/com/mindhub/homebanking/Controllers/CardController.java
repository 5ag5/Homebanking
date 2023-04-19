package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.DTOs.CardDTO;
import com.mindhub.homebanking.Repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CardController {
    @Autowired
    CardRepository cardRepository;

    @RequestMapping("/api/cards")
    public List<CardDTO> getCards(){
        return cardRepository.findAll().stream().map(Card -> new CardDTO(Card)).collect(Collectors.toList());
    }

}
