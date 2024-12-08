package com.exchange.utils;

import java.security.SecureRandom;

public class ReceiptHelper {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CHECK_NUMBER_LENGTH = 10;
    private static final SecureRandom random = new SecureRandom();

    public static String generateRandomCheckNumber() {
        StringBuilder checkNumber = new StringBuilder(CHECK_NUMBER_LENGTH);
        for (int i = 0; i < CHECK_NUMBER_LENGTH; i++) {
            checkNumber.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return checkNumber.toString();
    }
}
