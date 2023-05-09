package com.mindhub.homebanking;

import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.Repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest

@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {
    @Autowired
    LoanRepository loanRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Test
    public void existLoans(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, is(not(empty())));
        //assertThat(loans,is(not(empty())));
    }

    @Test
    public void existPersonalLoan(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Persona Loan"))));

    }

    @Test
    public void existsAccounts(){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts, is(not(empty())));
    }
    @Test
    public void findAccountById(){
        Account account = accountRepository.findByNumber("VIN001");
        assertThat(account.getNumber(), equalTo("VIN001"));
    }

    @Test
    public void balanceIsLessThan10000(){
        Account account = accountRepository.findByNumber("VIN002");
        assertThat(new BigDecimal(String.valueOf(account.getBalance())), lessThan(new BigDecimal("10000")));
    }
    @Test
    public void isTypeOf(){
        Client clients = clientRepository.findByEmail("melba@mindhub.com");
        assertThat(clients.getEmail(), isA(String.class));
    }

    @Test
    public void sizeOfArray(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients, hasSize(greaterThan(3)));
    }

    @Test
    public void cardTypTest(){
        List<Card> cards = cardRepository.findAll();
        for(Card card: cards){
            assertThat(card.getType(), not(equalTo("GIFT")));
        }
    }

    @Test
    public void lengthOfCVV(){
        List<Card> cards = cardRepository.findAll();
        for(Card card: cards){
            assertThat(String.valueOf(card.getCvv()).length(),is(equalTo(3)));
        }
    }

    @Test
    public void transactionDateTymeTrans1(){
    List<Transaction> transactions = transactionRepository.findAll();
        for(Transaction tansaction:transactions){
            assertThat(tansaction.getDate(),isA(LocalDateTime.class));
        }
    }

    @Test
    public void emptyTransactions(){
        List<Transaction> transactions = transactionRepository.findAll();
        for(Transaction transaction:transactions) {
            assertThat(transaction.getDate(), notNullValue());
            assertThat(transaction.getAccount(),notNullValue());
            assertThat(transaction.getDescription(),notNullValue());
            assertThat(transaction.getType(),notNullValue());
            assertThat(transaction.getAmount(),notNullValue());
        }
    }
}
