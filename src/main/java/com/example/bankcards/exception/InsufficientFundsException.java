package com.example.bankcards.exception;

import lombok.Getter;

public class InsufficientFundsException extends RuntimeException {
    @Getter
    private String cardNumber;

    public InsufficientFundsException(String cardNumber) {
        super("Not enough funds on card %s".formatted(cardNumber));
        this.cardNumber = cardNumber;
    }
}
