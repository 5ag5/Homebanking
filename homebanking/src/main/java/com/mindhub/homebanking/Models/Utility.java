package com.mindhub.homebanking.Models;

public class Utility {

    public Utility(){}

    public double generateRandomNumber(){
        int min = 1000;
        int max = 9999;
        double number = Math.round((Math.random() * (999 - 100) + 100));

        return number;
    }

}
