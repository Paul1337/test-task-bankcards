package com.example.bankcards.service;

import com.example.bankcards.entity.Role;
import com.example.bankcards.repository.RolesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RolesServiceTest {
    @Mock
    private RolesRepository rolesRepository;

    @InjectMocks
    private RolesService rolesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findRole_success() {
        Role role = mock(Role.class);
        when(rolesRepository.findById("USER")).thenReturn(Optional.of(role));
        assertSame(rolesService.findRole("USER"), role);
    }
}
