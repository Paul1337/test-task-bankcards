package com.example.bankcards.exception;

public class CardAlreadyExistException extends RuntimeException {
  public CardAlreadyExistException(String cardNumber) {
    super("Card with number " + cardNumber + " already exist");
  }
}
