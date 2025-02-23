package org.onlineticketing.utils;

import java.util.Random;
import java.util.random.RandomGenerator;

public class IdGenerator {


    public static String generateId(){
        Random random=new Random();
        int randomNumber=random.nextInt(1000);
        return Integer.toString(randomNumber);
    }
}
