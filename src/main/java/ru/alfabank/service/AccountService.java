package ru.alfabank.service;

import java.util.Random;

public class AccountService {

    public static void generate() {
        Random rand = new Random();
        int sleepTime = rand.nextInt(30001);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
