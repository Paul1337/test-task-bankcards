package com.example.bankcards.controller.advices.global;

import java.util.List;

import com.example.bankcards.controller.advices.global.responses.ValidationErrorResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ValidationExceptionControllerAdvice {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorResponse> handleMethodArgumentValidationExceptions(MethodArgumentNotValidException ex) {
    List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

    return ResponseEntity
                .badRequest()
                .body(new ValidationErrorResponse(String.join(". ", errors) + "."));
  }
}