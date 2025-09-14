package com.example.bankcards.dto.cards;

import lombok.Data;

import java.math.BigDecimal;

public class Transfer {

    @Data
    public static class Request {
        private String cardFrom;
        private String cardTo;
        private BigDecimal amount;
    }
}
