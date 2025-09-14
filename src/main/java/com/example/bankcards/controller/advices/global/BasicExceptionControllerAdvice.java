package com.example.bankcards.controller.advices.global;

import com.example.bankcards.controller.advices.global.responses.HttpErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class BasicExceptionControllerAdvice {
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<HttpErrorResponse> handleBasicException(RuntimeException ex) {
    return ResponseEntity.internalServerError().body(new HttpErrorResponse("SERVER_ERROR", ex.getMessage()));
  }
}