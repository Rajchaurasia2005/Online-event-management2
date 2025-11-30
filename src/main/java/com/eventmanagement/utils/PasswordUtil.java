package com.eventmanagement.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordUtil {
    private static final int STRENGTH = 12;

    public static String hashPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return BCrypt.withDefaults().hashToString(STRENGTH, password.toCharArray());
    }

    public static boolean verifyPassword(String password, String hash) {
        if (password == null || hash == null) {
            return false;
        }
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hash);
        return result.verified;
    }

    public static boolean isValidPassword(String password) {
        // Minimum 8 characters, at least one uppercase, one lowercase, one digit
        if (password == null || password.length() < 8) {
            return false;
        }
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");
    }
}