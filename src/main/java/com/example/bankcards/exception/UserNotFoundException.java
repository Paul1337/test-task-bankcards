package com.example.bankcards.exception;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException() {
    super("User does not exist");
  }

  public UserNotFoundException(String usernameOrId) {
    super("User '%s' does not exist".formatted(usernameOrId));
  }
}
