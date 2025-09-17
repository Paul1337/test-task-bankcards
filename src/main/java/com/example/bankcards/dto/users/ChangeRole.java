package com.example.bankcards.dto.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ChangeRole {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String newRole;
    }
}
