package com.example.bankcards.dto;

import java.time.Duration;

public class CreateCard {
    public static int DefaultActiveDaysCount = 365 * 3;

    public record Request(String number, String ownerId, int activeDaysCount) {
        public Request(String number, String ownerId) {
            this(number, ownerId, DefaultActiveDaysCount);
        }
    }
}
