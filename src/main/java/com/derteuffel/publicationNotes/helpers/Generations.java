package com.derteuffel.publicationNotes.helpers;

import java.util.Random;

public class Generations {
    public String generatedString(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        System.out.println(generatedString);
        return generatedString;
    }

    public int generateInt(){
        int random_int = (int)(Math.random()*100000);
        System.out.println(random_int);
        return random_int;
    }
}
