package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.DTOs.AccountDTO;
import com.mindhub.homebanking.DTOs.CreditCardDTO;
import com.mindhub.homebanking.Repositories.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CreditCardController {
    @Autowired
    CreditCardRepository creditCardRepository;

    @RequestMapping("/api/creditCards")
    public List<CreditCardDTO> getCreditCards(){
        return creditCardRepository.findAll().stream().map(creditCard -> new CreditCardDTO(creditCard)).collect(Collectors.toList());
    }

}
