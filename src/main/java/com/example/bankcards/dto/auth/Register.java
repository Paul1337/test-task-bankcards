package com.example.bankcards.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Register {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 50, message = "Username length must be between 3 and 50 characters")
        private String username;

        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name length must be at most 100 characters")
        private String name;

        @NotBlank(message = "Password is required")
        @Size(min = 6, max = 100, message = "Password length must be between 6 and 100 characters")
        private String password;
    }

}
