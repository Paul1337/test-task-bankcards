package com.example.bankcards.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.nio.file.attribute.UserPrincipal;

@AllArgsConstructor
public class AppUserPrincial implements UserPrincipal {
    @Getter
    private String name;

    @Getter
    private String username;

    @Getter
    private String userId;
}
