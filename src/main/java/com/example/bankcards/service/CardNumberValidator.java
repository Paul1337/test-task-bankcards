package com.example.bankcards.service;

import org.springframework.stereotype.Service;

@Service
public class CardNumberValidator {
  public void validateCardNumber(String number) {
    if (!isCardNumberValid(number)) {
      throw new RuntimeException("Card number is not valid: " + number);
    }
  }

  public boolean isCardNumberValid(String number) {
    if (number.length() != 16) return false;
    if (number.chars().anyMatch(c -> !Character.isDigit(c))) {
      return false;
    }
    return true;
  }
}
