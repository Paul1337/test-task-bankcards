package com.example.bankcards.service;

import com.example.bankcards.dto.users.ChangeRole;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.UserNotFoundException;
import com.example.bankcards.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private RolesService rolesService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserByUsername_success() {
        String username = "testUsername";
        User user = mock(User.class);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        assertSame(user, userService.getUserByUsername(username));
    }

    @Test
    void getUserByUsername_notFound() {
        String username = "testUsername";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserByUsername(username));
    }

    @Test
    void updateUserRole_success() {
        String username = "testUsername";
        ChangeRole.Request dto = new ChangeRole.Request("ADMIN");
        User user = mock(User.class);
        doNothing().when(user).setRole(any(Role.class));
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        Role newRole = mock(Role.class);
        when(rolesService.findRole(dto.getNewRole())).thenReturn(newRole);
        userService.updateUserRole(username, dto);
        verify(user).setRole(newRole);
        verify(userRepository).save(user);
    }

    @Test
    void deleteUser_success() {
        String username = "testUsername";
        doNothing().when(userRepository).deleteByUsername(username);
        userService.deleteUser(username);
        verify(userRepository).deleteByUsername(username);
    }
}
