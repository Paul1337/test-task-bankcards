package com.example.bankcards.dto.cards;

import com.example.bankcards.util.validators.card.ValidCardNumber;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

public class Transfer {

    @Data
    public static class Request {
        @NotBlank(message = "Source card should be provided")
        @ValidCardNumber
        private String cardFrom;

        @NotBlank(message = "Destination card should be provided")
        @ValidCardNumber
        private String cardTo;

        @NotBlank
        private BigDecimal amount;
    }
}
