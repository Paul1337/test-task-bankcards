package com.example.bankcards.exception;

public class CardNotFoundException extends RuntimeException {
  public CardNotFoundException(String cardNumber) {
    super("Card with number " + cardNumber + " does not exist");
  }
}
