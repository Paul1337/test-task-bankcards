package com.example.bankcards.controller.advices.global.responses;

public class ValidationErrorResponse extends HttpErrorResponse {
  public ValidationErrorResponse(String message) {
    super("VALIDATION_ERROR", message);
  }
}
