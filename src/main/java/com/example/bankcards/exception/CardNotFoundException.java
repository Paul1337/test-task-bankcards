package com.example.bankcards.exception;

import lombok.Data;
import lombok.Getter;

public class CardNotFoundException extends RuntimeException {
  @Getter
  private String cardNumber;

  public CardNotFoundException(String cardNumber) {
    super("Card with number " + cardNumber + " does not exist");

    this.cardNumber = cardNumber;
  }
}
