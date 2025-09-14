package com.example.bankcards.util.validators.card;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CardNumberValidator implements ConstraintValidator<ValidCardNumber, String> {
    @Override
    public boolean isValid(String cardNumber, ConstraintValidatorContext context) {
        if (cardNumber == null) return false;
        if (cardNumber.length() != 16) return false;
        if (cardNumber.chars().anyMatch(c -> !Character.isDigit(c))) {
            return false;
        }
        return true;
    }
}