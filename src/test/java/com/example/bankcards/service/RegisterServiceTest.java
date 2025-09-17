package com.example.bankcards.service;

import com.example.bankcards.dto.auth.Register;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.RoleNotFoundException;
import com.example.bankcards.exception.UserAlreadyExistException;
import com.example.bankcards.repository.RolesRepository;
import com.example.bankcards.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

public class RegisterServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private RolesRepository rolesRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegisterService registerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_success() {
        Role userRole = mock(Role.class);
        Register.Request dto = new Register.Request("testUsername", "testName", "testPassword");
        when(userRepository.existsByUsername(dto.getUsername())).thenReturn(false);

        String mockPasswordHash = "encodedPassword";
        when(passwordEncoder.encode(dto.getPassword())).thenReturn(mockPasswordHash);
        when(rolesRepository.findById("USER")).thenReturn(Optional.of(userRole));
        registerService.register(dto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals(dto.getUsername(), savedUser.getUsername());
        assertEquals(dto.getName(), savedUser.getName());
        assertEquals(mockPasswordHash, savedUser.getPassword());
    }

    @Test
    void register_userExists_throws() {
        Register.Request dto = new Register.Request("testUsername", "testName", "testPassword");
        when(userRepository.existsByUsername(dto.getUsername())).thenReturn(true);
        assertThrows(UserAlreadyExistException.class, () -> registerService.register(dto));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void register_roleNotFound() {
        Role userRole = mock(Role.class);
        Register.Request dto = new Register.Request("testUsername", "testName", "testPassword");
        when(userRepository.existsByUsername(dto.getUsername())).thenReturn(false);

        String mockPasswordHash = "encodedPassword";
        when(passwordEncoder.encode(dto.getPassword())).thenReturn(mockPasswordHash);
        when(rolesRepository.findById("USER")).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> registerService.register(dto));
        verify(userRepository, never()).save(any(User.class));
    }
}