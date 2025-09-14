package com.example.bankcards.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserInfo {
    private String username;
    private String role;
}
