package com.example.bankcards.dto;

import com.example.bankcards.entity.Card;

import java.time.LocalDateTime;

public class GetCard {
    public record FullResponse(
            String number,
            LocalDateTime expirationDate,
            Card.Status status
    ) {}

    public record ShallowResponse(
            String numberProtected
    ) {}
}
