package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.DTOs.LoanAplicationDTO;
import com.mindhub.homebanking.DTOs.LoanDTO;
import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.Repositories.*;
import com.mindhub.homebanking.Service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class LoanController {

    @Autowired
    LoanService loanService;

@Transactional
@RequestMapping(path="/api/loans", method= RequestMethod.POST)
public ResponseEntity<Object> loanRequest(Authentication authentication,
                                          @RequestBody LoanAplicationDTO loanAplication){
    return loanService.loanRequest(authentication,loanAplication);
}

@RequestMapping("/api/loans")
public List<LoanDTO> getAvailableLoans(){
    return loanService.getAvailableLoans();
}

}
