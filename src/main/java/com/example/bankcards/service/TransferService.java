package com.example.bankcards.service;

import com.example.bankcards.entity.Card;
import com.example.bankcards.exception.CardNotFoundException;
import com.example.bankcards.exception.InsufficientFundsException;
import com.example.bankcards.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.swing.*;
import java.math.BigDecimal;

@Service
public class TransferService {
    @Autowired
    private CardRepository cardRepository;

    @Transactional
    public void transferCash(String cardNumFrom, String cardNumTo, BigDecimal amount) {
        Assert.notNull(amount, "Transfer amount should not be null");
        Assert.isTrue(amount.compareTo(BigDecimal.ZERO) > 0, "Transfer amount should be more than zero");

        Card cardFrom = cardRepository.findById(cardNumFrom).orElseThrow(() -> new CardNotFoundException(cardNumFrom));
        Card cardTo = cardRepository.findById(cardNumTo).orElseThrow(() -> new CardNotFoundException(cardNumTo));

        if (cardFrom.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException(cardNumFrom);
        }

        cardTo.transferCashFrom(cardFrom, amount);
    }
}

