package com.example.bankcards.controller.advices.global;

import com.example.bankcards.controller.advices.global.responses.HttpErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityExceptionControllerAdvice {
    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<HttpErrorResponse> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new HttpErrorResponse("FORBIDDEN", ex.getMessage()));
    }
}
