package com.example.bankcards.security.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasRole('USER')")
public @interface RoleUser {
}
