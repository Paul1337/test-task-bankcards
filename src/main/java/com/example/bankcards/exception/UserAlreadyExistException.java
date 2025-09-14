package com.example.bankcards.exception;

public class UserAlreadyExistException extends RuntimeException {
  public UserAlreadyExistException() {
    super("User already exists");
  }

  public UserAlreadyExistException(String username) {
    super("User with username %s already exists".formatted(username));
  }
}
