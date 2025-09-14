package com.example.bankcards.controller.advices.cards;

import com.example.bankcards.controller.advices.global.responses.BadRequestErrorResponse;
import com.example.bankcards.controller.advices.global.responses.HttpErrorResponse;
import com.example.bankcards.exception.CardAlreadyExistException;
import com.example.bankcards.exception.CardNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CardsControllerAdvice {
    @ExceptionHandler({CardAlreadyExistException.class, CardNotFoundException.class})
    public ResponseEntity<HttpErrorResponse> handleCardsBadRequestExceptions(RuntimeException ex) {
        return ResponseEntity.badRequest().body(new BadRequestErrorResponse(ex.getMessage()));
    }
}
