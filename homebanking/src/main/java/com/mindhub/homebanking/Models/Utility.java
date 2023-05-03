package com.mindhub.homebanking.Models;

import java.util.Random;

public class Utility {
    private Random random = new Random();
    public Utility(){
    }

    public int generateRandomNumber(){
        return (int) (1000+ Math.random() * 9999);
    }

}
