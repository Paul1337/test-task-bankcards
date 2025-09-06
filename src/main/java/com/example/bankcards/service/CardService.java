package com.example.bankcards.service;

import com.example.bankcards.dto.CreateCard;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardNumberValidator cardNumberValidator;

    public void createCard(CreateCard.Request dto) {
        cardNumberValidator.validateCardNumber(dto.number());
        if (cardRepository.existsById(dto.number())) throw new RuntimeException(String.format("Card with number %s already exists", dto.number()));
        User owner = userRepository.findById(dto.ownerId()).orElseThrow(() -> new RuntimeException("Owner does not exist"));
        LocalDateTime expirationDateTime = LocalDateTime.now().plus(Duration.ofDays(dto.activeDaysCount()));
        Card newCard = new Card(dto.number(), owner, expirationDateTime);
        cardRepository.save(newCard);
    }



}
