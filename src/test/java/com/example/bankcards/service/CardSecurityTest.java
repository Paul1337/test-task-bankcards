package com.example.bankcards.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

class CardSecurityTest {
    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardSecurity cardSecurity;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    void testIsOwner_cardExistsAndOwnerMatches_returnsTrue() {
        String cardNum = "1111222233334444";
        String username = "testUser";
        when(authentication.getName()).thenReturn(username);

        User owner = mock(User.class);
        when(owner.getUsername()).thenReturn(username);

        Card card = mock(Card.class);
        when(card.getOwner()).thenReturn(owner);

        when(cardRepository.findById(cardNum)).thenReturn(Optional.of(card));

        boolean result = cardSecurity.isOwner(cardNum);

        assertTrue(result);
    }

    @Test
    void testIsOwner_cardExistsButOwnerIsNull_returnsFalse() {
        String cardNum = "1111222233334444";
        String username = "testUser";
        when(authentication.getName()).thenReturn(username);

        Card card = mock(Card.class);
        when(card.getOwner()).thenReturn(null);

        when(cardRepository.findById(cardNum)).thenReturn(Optional.of(card));

        boolean result = cardSecurity.isOwner(cardNum);

        assertFalse(result);
    }

    @Test
    void testIsOwner_cardExistsButOwnerUsernameDoesNotMatch_returnsFalse() {
        String cardNum = "1111222233334444";
        String username = "testUser";
        when(authentication.getName()).thenReturn(username);

        User owner = mock(User.class);
        when(owner.getUsername()).thenReturn("anotherUser");

        Card card = mock(Card.class);
        when(card.getOwner()).thenReturn(owner);

        when(cardRepository.findById(cardNum)).thenReturn(Optional.of(card));

        boolean result = cardSecurity.isOwner(cardNum);

        assertFalse(result);
    }

    @Test
    void testIsOwner_cardDoesNotExist_returnsFalse() {
        String cardNum = "1111222233334444";
        String username = "testUser";

        when(authentication.getName()).thenReturn(username);
        when(cardRepository.findById(cardNum)).thenReturn(Optional.empty());
        boolean result = cardSecurity.isOwner(cardNum);

        assertFalse(result);
    }
}
