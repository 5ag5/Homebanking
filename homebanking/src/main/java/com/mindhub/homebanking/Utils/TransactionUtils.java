package com.mindhub.homebanking.Utils;

import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Client;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class TransactionUtils {

    public static boolean clientAccountQuery(Client client, String numberOrigin) {
        Set<Account> tempSet = client.getAccounts();
        for(Account acct: tempSet){
            if(acct.getNumber().equals(numberOrigin)){
                return true;
            }
        }
        return false;
    }

    public static boolean accountAvailableFunds(Account accountClient, Double amount){
        if(accountClient.getBalance()< amount){
            return false;
        }
        return true;
    }

    public static LocalDateTime timeTransaction(){
        LocalDateTime creationDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String date1Tem = creationDate.format(formatter);
        return LocalDateTime.parse(date1Tem, formatter);
    }
}
