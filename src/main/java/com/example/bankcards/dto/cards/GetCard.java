package com.example.bankcards.dto.cards;

import com.example.bankcards.entity.Card;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class GetCard {
    @Data
    public static class FullResponse extends ShallowResponse {
      private BigDecimal balance;
    }

    @Data
    public static class ShallowResponse {
        private String number;
        private LocalDateTime expirationDate;
        private Card.Status status;
        private UUID ownerId;
    }
}
