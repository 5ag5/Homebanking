package com.mindhub.homebanking.Utils;

import com.mindhub.homebanking.Models.Card;
import com.mindhub.homebanking.Models.CardType;

import java.util.Set;

public final class CardUtils {

    private CardUtils(){

    }

    public static String getCardNumber() {
        return numbersseries() + "-" +  numbersseries() + "-" +  numbersseries() + "-" +  numbersseries();
    }

    private static int numbersseries() {
        return (int) (1000+ Math.random() * 9999);
    }

    public static int getCVV(){
        return (int) Math.round(Math.random() * (999 - 100) + 100);
    }

    public static boolean numberOfCreditCards(Set<Card> clientCards, CardType cardType) {
        int countCredit =0;

        for (Card card:clientCards){
            if(card.getType() == CardType.CREDIT && card.isStatus()){
                countCredit = countCredit+1;
            }
        }

        if(cardType == CardType.CREDIT ){
            countCredit = countCredit+ 1;
        }

        return countCredit > 3;
    }

    public static boolean numberOfDebitCards(Set<Card> clientCards, CardType cardType) {
        int countDebit =0;

        for (Card card:clientCards){
            if(card.getType() == CardType.DEBIT && card.isStatus()){
                countDebit = countDebit+1;
            }
        }

        if(cardType == CardType.DEBIT){
            countDebit = countDebit +1;
        }

        return countDebit > 3;
    }
}
