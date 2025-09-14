package com.example.bankcards.dto.cards;

import lombok.Data;

public class GetCardList {
    @Data
    public static class Request {
        private int page;
        private int size;
        private String search = "";
    }
}
