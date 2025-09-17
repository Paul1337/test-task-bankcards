package com.example.bankcards.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Optional;

import com.example.bankcards.entity.Card;
import com.example.bankcards.exception.CardNotFoundException;
import com.example.bankcards.exception.InsufficientFundsException;
import com.example.bankcards.repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class TransferServiceTest {
    @Mock
    private CardRepository cardRepository;
    @InjectMocks
    private TransferService transferService;

    private Card cardFrom;
    private Card cardTo;

    @BeforeEach
    void setUp() {
        String from = "1111222233334444";
        String to = "1111222233335555";
        MockitoAnnotations.openMocks(this);


        cardFrom = createCard(from, BigDecimal.valueOf(200));
        cardTo = createCard(to, BigDecimal.valueOf(100));

        when(cardRepository.findById(from)).thenReturn(Optional.of(cardFrom));
        when(cardRepository.findById(to)).thenReturn(Optional.of(cardTo));
    }

    private Card createCard(String cardNum, BigDecimal balance) {
        Card card = mock(Card.class);
        when(card.getBalance()).thenReturn(balance);
        when(card.getNumber()).thenReturn(cardNum);
        doNothing().when(card).transferCashFrom(any(Card.class), any(BigDecimal.class));
        return card;
    }

    @Test
    void testSuccessfulTransfer() {
        BigDecimal amount = BigDecimal.valueOf(100);
        assertDoesNotThrow(() -> transferService.transferCash(cardFrom.getNumber(), cardTo.getNumber(), amount));
        verify(cardTo).transferCashFrom(cardFrom, amount);
    }

    @Test
    void testTransferAmountNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            transferService.transferCash(cardFrom.getNumber(), cardTo.getNumber(), null);
        });
    }

    @Test
    void testTransferAmountZeroOrNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            transferService.transferCash(cardFrom.getNumber(), cardTo.getNumber(), BigDecimal.ZERO);
        });
    }

    @Test
    void testCardFromNotFound() {
        when(cardRepository.findById(cardFrom.getNumber())).thenReturn(Optional.empty());

        CardNotFoundException ex = assertThrows(CardNotFoundException.class, () -> {
            transferService.transferCash(cardFrom.getNumber(), cardTo.getNumber(), BigDecimal.TEN);
        });
        assertEquals(cardFrom.getNumber(), ex.getCardNumber());
    }

    @Test
    void testCardToNotFound() {
        when(cardRepository.findById(cardTo.getNumber())).thenReturn(Optional.empty());

        CardNotFoundException ex = assertThrows(CardNotFoundException.class, () -> {
            transferService.transferCash(cardFrom.getNumber(), cardTo.getNumber(), BigDecimal.TEN);
        });
        assertEquals(cardTo.getNumber(), ex.getCardNumber());
    }

    @Test
    void testInsufficientFunds() {
        InsufficientFundsException ex = assertThrows(InsufficientFundsException.class, () -> {
            transferService.transferCash(cardFrom.getNumber(), cardTo.getNumber(),  BigDecimal.valueOf(500));
        });
        assertEquals(cardFrom.getNumber(), ex.getCardNumber());
    }
}
