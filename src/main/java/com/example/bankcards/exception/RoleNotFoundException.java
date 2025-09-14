package com.example.bankcards.exception;

public class RoleNotFoundException extends RuntimeException {
  public RoleNotFoundException() {
    super("Role does not exist");
  }

  public RoleNotFoundException(String roleValue) {
    super("Role %s does not exist".formatted(roleValue));
  }
}
