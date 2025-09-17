package com.example.bankcards.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Set;

class CardAccessServiceTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private CardAccessService cardAccessService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    void testUserOwnsCard_whenUserHasCard_returnsTrue() {
        String cardNum = "1111222233334444";
        String username = "testUser";

        when(authentication.getName()).thenReturn(username);

        Card card = mock(Card.class);
        when(card.getNumber()).thenReturn(cardNum);

        User user = mock(User.class);
        when(user.getCards()).thenReturn(List.of(card));

        when(userService.getUserByUsername(username)).thenReturn(user);

        boolean result = cardAccessService.userOwnsCard(cardNum);

        assertTrue(result);
    }

    @Test
    void testUserOwnsCard_whenUserDoesNotHaveCard_returnsFalse() {
        String cardNum = "1111222233334444";
        String username = "testUser";

        when(authentication.getName()).thenReturn(username);

        Card card = mock(Card.class);
        when(card.getNumber()).thenReturn("4444333322221111");

        User user = mock(User.class);
        when(user.getCards()).thenReturn(List.of(card));

        when(userService.getUserByUsername(username)).thenReturn(user);

        boolean result = cardAccessService.userOwnsCard(cardNum);

        assertFalse(result);
    }
}
