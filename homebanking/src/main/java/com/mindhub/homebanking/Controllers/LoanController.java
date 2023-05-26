package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.DTOs.*;
import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.Repositories.*;
import com.mindhub.homebanking.Service.AccountService;
import com.mindhub.homebanking.Service.ClientService;
import com.mindhub.homebanking.Service.LoanService;
import com.mindhub.homebanking.Service.TransactionService;
import com.mindhub.homebanking.Utils.LoanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class LoanController {

    @Autowired
    LoanService loanService;

    @Autowired
    AccountService accountService;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ClientService clientService;

    @Autowired
    TransactionService transactionService;

@Transactional
@PostMapping(path="/api/loans")
public ResponseEntity<Object> loanRequest(Authentication authentication,
                                          @RequestBody LoanAplicationDTO loanAplication){
    //return loanService.loanRequest(authentication,loanAplication);


    Client client = clientService.findClient(authentication);
    Account accountDestination = accountService.findAccountByNunber(loanAplication);
    Loan loanSolicited = loanService.findLoanByName(loanAplication);

//Verificar que los datos sean correctos, es decir no estén vacíos, que el monto no sea 0 o que las cuotas no sean 0.

    if(loanAplication.getAmount() == 0 || loanAplication.getPayments() == 0 || loanAplication.getTipoLoan().isBlank() || loanAplication.getAccountDestination().isBlank()){
        return new ResponseEntity<>("Values are empty, pleaser fill in all", HttpStatus.FORBIDDEN);
    }

    if(loanService.findLoanByName(loanAplication) == null){
        return new ResponseEntity<>("Loan type doesn't excist", HttpStatus.FORBIDDEN);
    }

    //Verificar que el monto solicitado no exceda el monto máximo del préstamo

    if(maximumAmountAllowedTypeLoan(loanAplication.getAmount(), loanAplication.getTipoLoan())){
        return new ResponseEntity<>("Solicited amount exceeds Maximum amount allowed for the type of loan", HttpStatus.FORBIDDEN );
    }

    if(LoanUtils.counterOfLoans(client, loanAplication)) {
        return new ResponseEntity<>("Request exceeds the maximum number Loans allowed for the type " + loanAplication.getTipoLoan() + " Loan, which is 3", HttpStatus.FORBIDDEN );
    }

    //Verifica que la cantidad de cuotas se encuentre entre las disponibles del préstamo

    if(queryAvailableNumberOfPayments(loanAplication.getTipoLoan(), loanAplication.getPayments()) == false){
        return new ResponseEntity<>("Available number of payments chose doesn't excist",HttpStatus.FORBIDDEN);
    }

    //Verificar que la cuenta de destino exista
    if(loanService.findAccountByNunber(loanAplication) == null){
        return new ResponseEntity<>("Account destination, doesn't excist", HttpStatus.CONFLICT);
    }

    //Verificar que la cuenta de destino pertenezca al cliente autenticado
    if(LoanUtils.queryAccountBelongsToClient(client, loanAplication.getAccountDestination()) == false){
        return new ResponseEntity<>("Account destination, doesn't belong to client", HttpStatus.FORBIDDEN);
    }

    double interestOfLoan = loanService.queryInterestRate(loanAplication.getTipoLoan());

    //Se debe crear una solicitud de préstamo con el monto solicitado sumando el 20% del mismo
    ClientLoan newLoan = new ClientLoan(loanAplication.getAmount()*1.20, LoanUtils.numbersseriesLoan() ,loanAplication.getPayments(),loanAplication.getTipoLoan(),interestOfLoan);
    Transaction transactionNewloan = new Transaction(TypeTransaction.CREDITO, newLoan.getAmount(),
            loanAplication.getTipoLoan() + " loan approved",Utility.timeTransaction(),accountDestination.getBalance()+newLoan.getAmount());

    accountDestination.setBalance(accountDestination.getBalance() +transactionNewloan.getAmount());
    accountDestination.addTransaction(transactionNewloan);
    client.addLoan(newLoan);
    loanSolicited.addClientLoan(newLoan);

    loanService.SaveClientLoan(newLoan);
    transactionService.SaveTransaction(transactionNewloan);
    accountService.SaveAccount(accountDestination);
    loanService.SaveLoan(loanSolicited);

    return new ResponseEntity<>(HttpStatus.ACCEPTED);
}

@GetMapping("/api/loans")
public List<LoanDTO> getAvailableLoans(){
    return loanService.getAvailableLoans();
}

@PostMapping(path="/api/loans/newLoan")
    public ResponseEntity<Object> newTypeOfLoan(@RequestBody NewLoanDTO newLoanDTO){
        //return loanService.createNewTypeOfLoan(newLoanDTO);

    if(loanService.findByName(newLoanDTO.getName()) != null) {
        return new ResponseEntity<>("Loan already excists, it cannot be created",HttpStatus.FORBIDDEN);
    }else {
        Loan newLoan = new Loan(newLoanDTO.getName(), newLoanDTO.getMaxAmount(),
                newLoanDTO.getPayments(), newLoanDTO.getInterest(), LoanUtils.interestsPayments);
        loanService.SaveLoan(newLoan);
    }
    return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(path="/api/clients/current/loans/payment")
    public ResponseEntity<Object>paymentLoan(Authentication authenticated, @RequestParam String serialNumber,
                                             @RequestParam int numberOfPayments, @RequestParam String accountNumber){
        //return loanService.paymentClientLoan(authenticated, serialNumber, numberOfPayments,accountNumber);

        ClientLoan loan = loanService.findBySerialNumber(serialNumber);
        Account account =  accountService.findByNumber(accountNumber);
        Client client =  clientRepository.findByEmail(authenticated.getName());

        if (numberOfPayments > loan.getPayments()){
            return new ResponseEntity<>("Payments surpass the number of outstanding payments",HttpStatus.FORBIDDEN);
        }

        if(numberOfPayments == 0){
            return new ResponseEntity<>("0 payments chosen not allowed",HttpStatus.FORBIDDEN);
        }

        if(numberOfPayments < 0){
            return new ResponseEntity<>("negative number chosen",HttpStatus.FORBIDDEN);
        }

        if(loan == null){
            return new ResponseEntity<>("System didn't find Loan.",HttpStatus.FORBIDDEN);
        }

        double paymentToBePayed = (loan.getAmount()/ loan.getPayments())*numberOfPayments;

        if(paymentToBePayed > account.getBalance()){
            return new ResponseEntity<>("You don't have enough funds", HttpStatus.FORBIDDEN);
        }

        loan.setAmount(loan.getAmount()-paymentToBePayed);
        loan.setPayments(loan.getPayments()-numberOfPayments);

        Transaction transaction = new Transaction(TypeTransaction.DEBITO,paymentToBePayed,
                "Payment Loan " + loan.getSerialNumber(), LocalDateTime.now(),
                account.getBalance()-paymentToBePayed);

        account.setBalance(account.getBalance()-paymentToBePayed);

        accountService.SaveAccount(account);
        account.addTransaction(transaction);
        transactionService.SaveTransaction(transaction);
        //SaveClientLoan(loan);
        boolean result = LoanUtils.verifyIfBalanceIs0(loan);

        if(result){
            loan.setStatus(false);
        }

        loanService.SaveClientLoan(loan);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    private boolean maximumAmountAllowedTypeLoan(double amount, String type) {
        Loan loanName = loanService.findLoanTypeByName(type);
        if(loanName != null){
            if(loanName.getMaxAmount() <= amount){
                return true;
            }
        }
        return false;
    }

    private boolean queryAvailableNumberOfPayments(String typeloan, int payments) {
        Loan loanTemp = loanService.findLoanTypeByName(typeloan);
        List<Integer> listTemp = loanTemp.getPayments();
        for(Integer entry: listTemp){
            if(entry == payments){
                return true;
            }
        }
        return false;
    }


}