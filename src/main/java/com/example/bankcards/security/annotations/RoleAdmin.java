package com.example.bankcards.security.annotations;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasRole('ADMIN')")
public @interface RoleAdmin {
}
