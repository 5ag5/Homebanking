package com.mindhub.homebanking.Service.implement;

import com.mindhub.homebanking.DTOs.LoanAplicationDTO;
import com.mindhub.homebanking.DTOs.LoanDTO;
import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.Repositories.*;
import com.mindhub.homebanking.Service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LoanServiceImplements implements LoanService {
    @Autowired
    ClientLoanRepository clientLoanRepository;

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    LoanRepository loanRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientRepository clientRepository;

    @Override
    public void SaveClientLoan(ClientLoan clientLoan) {
        clientLoanRepository.save(clientLoan);
    }

    @Override
    public void SaveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);

    }

    @Override
    public void SaveAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public void SaveLoan(Loan loan) {
        loanRepository.save(loan);
    }

    @Override
    public Client findCLientById(Authentication authentication){
        return clientRepository.findByEmail(authentication.getName());
    }

    @Override
    public Loan findLoanByName(LoanAplicationDTO loanAplication){
        return loanRepository.findByName(loanAplication.getTipoLoan());
    }

    @Override
    public Account findAccountByNunber(LoanAplicationDTO loanAplication){
        return accountRepository.findByNumber(loanAplication.getAccountDestination());
    }

    @Override
    public ResponseEntity<Object> loanRequest(Authentication authentication, LoanAplicationDTO loanAplication) {

        Client client = findCLientById(authentication);
        Account accountDestination = findAccountByNunber(loanAplication);
        Loan loanSolicited = findLoanByName(loanAplication);

//Verificar que los datos sean correctos,
// es decir no estén vacíos, que el monto
// no sea 0 o que las cuotas no sean 0.

        if(loanAplication.getAmount() == 0 || loanAplication.getPayments() == 0 || loanAplication.getTipoLoan().isBlank() || loanAplication.getAccountDestination().isBlank()){
            return new ResponseEntity<>("Values are empty, pleaser fill in all", HttpStatus.FORBIDDEN);
        }

        if(findLoanByName(loanAplication) == null){
            return new ResponseEntity<>("Loan type doesn't excist", HttpStatus.FORBIDDEN);
        }

        //Verificar que el monto solicitado no exceda el monto máximo del préstamo

        if(maximumAmountAllowedTypeLoan(loanAplication.getAmount(), loanAplication.getTipoLoan())){
            return new ResponseEntity<>("Solicited amount exceeds Maximum amount allowed for the type of loan", HttpStatus.FORBIDDEN );
        }

        if(counterOfLoans(client, loanAplication)) {
            return new ResponseEntity<>("Request exceeds the maximum number Loans allowed for the type " + loanAplication.getTipoLoan() + " Loan, which is 3", HttpStatus.FORBIDDEN );
        }

        //Verifica que la cantidad de cuotas se encuentre entre las disponibles del préstamo

        if(queryAvailableNumberOfPayments(loanAplication.getTipoLoan(), loanAplication.getPayments()) == false){
            return new ResponseEntity<>("Available number of payments chose doesn't excist",HttpStatus.FORBIDDEN);
        }

        //Verificar que la cuenta de destino exista
        if(findAccountByNunber(loanAplication) == null){
            return new ResponseEntity<>("Account destination, doesn't excist", HttpStatus.CONFLICT);
        }

        //Verificar que la cuenta de destino pertenezca al cliente autenticado
        if(queryAccountBelongsToClient(client, loanAplication.getAccountDestination()) == false){
            return new ResponseEntity<>("Account destination, doesn't belong to client", HttpStatus.FORBIDDEN);
        }

        double interestOfLoan = queryInterestRate(loanAplication.getTipoLoan());

        //Se debe crear una solicitud de préstamo con el monto solicitado sumando el 20% del mismo
        ClientLoan newLoan = new ClientLoan(loanAplication.getAmount()*1.20,
                loanAplication.getPayments(),loanAplication.getTipoLoan(),interestOfLoan);
        Transaction transactionNewloan = new Transaction(TypeTransaction.CREDITO, newLoan.getAmount(),
                loanAplication.getTipoLoan() + " loan approved",Utility.timeTransaction());

        accountDestination.setBalance(accountDestination.getBalance() +transactionNewloan.getAmount());
        accountDestination.addTransaction(transactionNewloan);
        client.addLoan(newLoan);
        loanSolicited.addClientLoan(newLoan);

        SaveClientLoan(newLoan);
        SaveTransaction(transactionNewloan);
        SaveAccount(accountDestination);
        SaveLoan(loanSolicited);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Override
    public List<LoanDTO> getAvailableLoans() {
        return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }

    private boolean counterOfLoans(Client client, LoanAplicationDTO loanAplication) {
        int counterTypeOfLoans =0;
        Set<ClientLoan> setClients = client.getClientLoans();

        for(ClientLoan clientLoan:setClients) {
            if(Objects.equals(loanAplication.getTipoLoan(), clientLoan.getTipoLoan())){
                counterTypeOfLoans = counterTypeOfLoans +1;
            }
        }

        return counterTypeOfLoans > 2;
    }

    private double queryInterestRate(String name) {
        Loan loan = loanRepository.findByName(name);
        return loan.getInterest();
    }


    private boolean queryAvailableNumberOfPayments(String typeloan, int payments) {
        Loan loanTemp = loanRepository.findByName(typeloan);
        List<Integer> listTemp = loanTemp.getPayments();
        for(Integer entry: listTemp){
            if(entry == payments){
                return true;
            }
        }
        return false;
    }

    private boolean maximumAmountAllowedTypeLoan(double amount, String type) {
        Loan loanName = loanRepository.findByName(type);
        if(loanName != null){
            if(loanName.getMaxAmount() < amount){
                return true;
            }
        }
        return false;
    }


    private boolean queryAccountBelongsToClient(Client client, String account) {
        Set<Account> setTemp = client.getAccounts();
        for(Account element: setTemp){
            if(element.getNumber().equals(account)){
                return true;
            }
        }
        return false;
    }
}
