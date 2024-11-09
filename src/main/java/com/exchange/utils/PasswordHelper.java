package com.exchange.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHelper {

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static Boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
