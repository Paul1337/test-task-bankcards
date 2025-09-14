package com.example.bankcards.controller.advices.global.responses;

public class BadRequestErrorResponse extends HttpErrorResponse {
    public BadRequestErrorResponse(String message) {
        super("BAD_REQUEST", message);
    }
}
