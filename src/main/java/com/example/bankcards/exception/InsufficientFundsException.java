package com.example.bankcards.exception;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException() {
        super("Not enough funds");
    }

    public InsufficientFundsException(String message) {
        super("Not enough funds on card %s".formatted(message));
    }
}
