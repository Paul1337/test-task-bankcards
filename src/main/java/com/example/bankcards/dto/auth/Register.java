package com.example.bankcards.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Register {
    @Data
    public static class Request {
        private String username;
        private String name;
        private String password;
    }

}
