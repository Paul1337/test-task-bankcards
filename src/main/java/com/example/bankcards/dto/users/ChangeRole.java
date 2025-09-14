package com.example.bankcards.dto.users;

import lombok.Data;

public class ChangeRole {
    @Data
    public static class Request {
        private String newRole;
    }
}
