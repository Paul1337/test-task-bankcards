package com.example.bankcards.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import com.example.bankcards.dto.cards.CreateCard;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.CardAlreadyExistException;
import com.example.bankcards.exception.CardNotFoundException;
import com.example.bankcards.exception.UserNotFoundException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

public class CardServiceTest {
    @InjectMocks
    private CardService cardService;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createCard_success() {
        CreateCard.Request dto = new CreateCard.Request();
        dto.setNumber("1111222233334444");
        dto.setOwnerId(UUID.randomUUID().toString());
        dto.setActiveDaysCount(10);

        when(cardRepository.existsById(dto.getNumber())).thenReturn(false);
        UUID ownerId = UUID.fromString(dto.getOwnerId());
        User owner = mock(User.class);
        when(owner.getId()).thenReturn(ownerId);
        when(userRepository.findById(ownerId)).thenReturn(Optional.of(owner));

        Card createdCard = cardService.createCard(dto);

        assertEquals(dto.getNumber(), createdCard.getNumber());
        assertEquals(ownerId, createdCard.getOwner().getId());
        assertTrue(createdCard.getExpirationDateTime().isAfter(LocalDateTime.now()));

        verify(cardRepository).save(any(Card.class));
    }

    @Test
    public void createCard_cardAlreadyExists_throws() {
        CreateCard.Request dto = new CreateCard.Request();
        dto.setNumber("1111222233334444");
        dto.setOwnerId(UUID.randomUUID().toString());
        dto.setActiveDaysCount(10);

        when(cardRepository.existsById(dto.getNumber())).thenReturn(true);

        assertThrows(CardAlreadyExistException.class, () -> cardService.createCard(dto));
        verify(cardRepository, never()).save(any());
    }

    @Test
    public void createCard_userNotFound_throws() {
        CreateCard.Request dto = new CreateCard.Request();
        dto.setNumber("1111222233334444");
        dto.setOwnerId(UUID.randomUUID().toString());
        dto.setActiveDaysCount(10);

        when(cardRepository.existsById(dto.getNumber())).thenReturn(false);
        when(userRepository.findById(UUID.fromString(dto.getOwnerId()))).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> cardService.createCard(dto));
        verify(cardRepository, never()).save(any());
    }

    @Test
    public void activateCard_success() {
        String cardNumber = "1111222233334444";
        Card card = mock(Card.class);
        when(cardRepository.findById(cardNumber)).thenReturn(Optional.of(card));
        when(card.getStatus()).thenReturn(Card.Status.Blocked);

        cardService.activateCard(cardNumber);

        verify(card).activate();
        verify(cardRepository).save(card);
    }

    @Test
    public void activateCard_alreadyActive_noSave() {
        String cardNumber = "123456";
        Card card = mock(Card.class);
        when(cardRepository.findById(cardNumber)).thenReturn(Optional.of(card));
        when(card.getStatus()).thenReturn(Card.Status.Active);

        cardService.activateCard(cardNumber);

        verify(card, never()).activate();
        verify(cardRepository, never()).save(any());
    }

    @Test
    public void activateCard_cardNotFound_throws() {
        String cardNumber = "1111222233334444";
        when(cardRepository.findById(cardNumber)).thenReturn(Optional.empty());
        assertThrows(CardNotFoundException.class, () -> cardService.activateCard(cardNumber));
    }

    @Test
    public void blockCard_success() {
        String cardNumber = "1111222233334444";
        Card card = mock(Card.class);
        when(cardRepository.findById(cardNumber)).thenReturn(Optional.of(card));
        when(card.getStatus()).thenReturn(Card.Status.Active);

        cardService.blockCard(cardNumber);

        verify(card).block();
        verify(cardRepository).save(card);
    }

    @Test
    public void blockCard_alreadyBlocked_noSave() {
        String cardNumber = "1111222233334444";
        Card card = mock(Card.class);
        when(cardRepository.findById(cardNumber)).thenReturn(Optional.of(card));
        when(card.getStatus()).thenReturn(Card.Status.Blocked);

        cardService.blockCard(cardNumber);

        verify(card, never()).block();
        verify(cardRepository, never()).save(any());
    }

    @Test
    public void blockCard_cardNotFound_throws() {
        String cardNumber = "1111222233334444";
        when(cardRepository.findById(cardNumber)).thenReturn(Optional.empty());
        assertThrows(CardNotFoundException.class, () -> cardService.blockCard(cardNumber));
    }

    @Test
    public void deleteCard_success() {
        String cardNumber = "1111222233334444";
        Card card = mock(Card.class);
        when(cardRepository.findById(cardNumber)).thenReturn(Optional.of(card));
        cardService.deleteCard(cardNumber);
        verify(cardRepository).delete(card);
    }

    @Test
    public void deleteCard_cardNotFound_throws() {
        String cardNumber = "1111222233334444";
        when(cardRepository.findById(cardNumber)).thenReturn(Optional.empty());
        assertThrows(CardNotFoundException.class, () -> cardService.deleteCard(cardNumber));
    }

    @Test
    public void getAllCards_returnsPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Card> page = mock(Page.class);
        when(cardRepository.findAll(pageable)).thenReturn(page);

        Page<Card> result = cardService.getAllCards(pageable);

        assertSame(page, result);
        verify(cardRepository).findAll(pageable);
    }

    @Test
    public void getCardsByUsername_success() {
        String username = "user1";
        Pageable pageable = PageRequest.of(0, 10);
        String search = "123";

        User user = mock(User.class);
        var userId = UUID.randomUUID();
        when(user.getId()).thenReturn(userId);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        Page<Card> page = mock(Page.class);
        when(cardRepository.findByOwnerIdAndNumberStartingWith(user.getId(), search, pageable)).thenReturn(page);

        Page<Card> result = cardService.getCardsByUsername(username, pageable, search);

        assertSame(page, result);
        verify(userRepository).findByUsername(username);
        verify(cardRepository).findByOwnerIdAndNumberStartingWith(user.getId(), search, pageable);
    }

    @Test
    public void getCardsByUsername_userNotFound_throws() {
        String username = "user1";
        Pageable pageable = PageRequest.of(0, 10);
        String search = "123";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> cardService.getCardsByUsername(username, pageable, search));

        verify(cardRepository, never()).findByOwnerIdAndNumberStartingWith(any(), any(), any());
    }
}

