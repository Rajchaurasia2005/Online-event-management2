package com.eventmanagement.utils;

import java.util.regex.Pattern;

public class ValidationUtil {
    private static final String EMAIL_PATTERN =
            "^[A-Za-z0-9+_.-]+@(.+)$";

    private static final Pattern EMAIL_REGEX =
            Pattern.compile(EMAIL_PATTERN);

    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return EMAIL_REGEX.matcher(email).matches();
    }

    public static boolean isValidUsername(String username) {
        if (username == null || username.length() < 4 || username.length() > 20) {
            return false;
        }
        return username.matches("^[a-zA-Z0-9_-]+$");
    }

    public static boolean isValidFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return false;
        }
        return fullName.matches("^[a-zA-Z\\s]{2,50}$");
    }

    public static boolean isValidEventTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return false;
        }
        return title.length() >= 3 && title.length() <= 100;
    }

    public static String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll("[<>\"'%;()&+]", "");
    }
}