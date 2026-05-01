package com.airtribe.learntrack.util;

import com.airtribe.learntrack.exception.InvalidInputException;

/**
 * InputValidator provides static helper methods to validate user input
 * before it reaches the service layer.
 */
public class InputValidator {

    private InputValidator() {}

    /**
     * Throws InvalidInputException if the string is null or blank.
     */
    public static void requireNonEmpty(String value, String fieldName) throws InvalidInputException {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty.");
        }
    }

    /**
     * Throws InvalidInputException if the number is not positive.
     */
    public static void requirePositive(int value, String fieldName) throws InvalidInputException {
        if (value <= 0) {
            throw new InvalidInputException(fieldName + " must be a positive number.");
        }
    }

    /**
     * Basic email format check — must contain '@' and a dot after it.
     */
    public static void requireValidEmail(String email) throws InvalidInputException {
        if (email == null || !email.contains("@") || email.indexOf('.', email.indexOf('@')) == -1) {
            throw new InvalidInputException("'" + email + "' is not a valid email address.");
        }
    }
}
