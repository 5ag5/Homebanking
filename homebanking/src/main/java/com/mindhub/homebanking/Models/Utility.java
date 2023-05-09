package com.mindhub.homebanking.Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Utility {
    private Random random = new Random();
    public Utility(){
    }

    public static int generateRandomNumber(){
        return (int) (1000+ Math.random() * 9999);
    }

    public static LocalDateTime timeTransaction(){
        LocalDateTime creationDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String date1Tem = creationDate.format(formatter);
        return LocalDateTime.parse(date1Tem, formatter);
    }

}
