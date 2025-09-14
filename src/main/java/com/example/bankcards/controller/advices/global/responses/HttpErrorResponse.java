package com.example.bankcards.controller.advices.global.responses;

import lombok.Getter;

public class HttpErrorResponse {
  @Getter
  private final String errorType;
  @Getter
  private final String message;

  public HttpErrorResponse(String errorType, String message) {
    this.errorType = errorType;
    this.message = message;
  }
}
