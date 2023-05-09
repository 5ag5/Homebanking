package com.mindhub.homebanking.Utils;

import com.mindhub.homebanking.DTOs.LoanAplicationDTO;
import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Models.ClientLoan;
import com.mindhub.homebanking.Models.Loan;
import com.mindhub.homebanking.Service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class LoanUtils {

    @Autowired
    LoanService loanService;

    public static double [][] interestsPayments = {{6,0.12},{12,0.10},{24,0.08},{36,0.05},{48,0.02}};


    public static String numbersseriesLoan() {
        return "LON" + (int) (000010+ Math.random() * 999999);
    }

    public static boolean verifyIfBalanceIs0(ClientLoan loan) {
        if(loan.getAmount() <= 0){
            //loan.setStatus(false);
            return true;
        }
        //SaveClientLoan(loan);
        return false;
    }


    public static boolean counterOfLoans(Client client, LoanAplicationDTO loanAplication) {
        int counterTypeOfLoans =0;
        Set<ClientLoan> setClients = client.getClientLoans();

        for(ClientLoan clientLoan:setClients) {
            if(Objects.equals(loanAplication.getTipoLoan(), clientLoan.getTipoLoan())){
                counterTypeOfLoans = counterTypeOfLoans +1;
            }
        }

        return counterTypeOfLoans > 2;
    }

    public static boolean queryAccountBelongsToClient(Client client, String account) {
        Set<Account> setTemp = client.getAccounts();
        for(Account element: setTemp){
            if(element.getNumber().equals(account)){
                return true;
            }
        }
        return false;
    }


}
