package com.example.bankcards.dto.cards;

import com.example.bankcards.util.validators.card.ValidCardNumber;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CreateCard {
    public static int DefaultActiveDaysCount = 365 * 3;

    @Data
    @NoArgsConstructor
    public static class Request {
        @NotBlank(message = "Card number should be provided")
        @ValidCardNumber
        private String number;

        @NotBlank(message = "Owner id is required")
        private String ownerId;

        private int activeDaysCount = DefaultActiveDaysCount;
    }
}
