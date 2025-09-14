package com.example.bankcards.service;

import com.example.bankcards.dto.cards.CreateCard;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.CardAlreadyExistException;
import com.example.bankcards.exception.CardNotFoundException;
import com.example.bankcards.exception.UserNotFoundException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class CardService {
//    private static int PageSize = 20;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private UserRepository userRepository;

    public Card createCard(CreateCard.Request dto) {
        if (cardRepository.existsById(dto.getNumber())) throw new CardAlreadyExistException(dto.getNumber());
        User owner = userRepository.findById(UUID.fromString(dto.getOwnerId())).orElseThrow(() -> new UserNotFoundException(dto.getOwnerId()));
        LocalDateTime expirationDateTime = LocalDateTime.now().plus(Duration.ofDays(dto.getActiveDaysCount()));
        Card newCard = new Card(dto.getNumber(), owner, expirationDateTime);
        cardRepository.save(newCard);
        return newCard;
    }

    public void activateCard(String cardNumber) {
      Card card = cardRepository.findById(cardNumber).orElseThrow(() -> new CardNotFoundException(cardNumber));
      if (card.getStatus() == Card.Status.Active) return;
      card.activate();
      cardRepository.save(card);
    }

    public void blockCard(String cardNumber) {
      Card card = cardRepository.findById(cardNumber).orElseThrow(() -> new CardNotFoundException(cardNumber));
      if (card.getStatus() == Card.Status.Blocked) return;
      card.block();
      cardRepository.save(card);
    }

    public void deleteCard(String cardNumber) {
        Card card = cardRepository.findById(cardNumber).orElseThrow(() -> new CardNotFoundException(cardNumber));
        cardRepository.delete(card);
    }

    public Page<Card> getAllCards(Pageable pageable) {
        return cardRepository.findAll(pageable);
    }

    public Page<Card> getCardsByUsername(String username, Pageable pageable, String search) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return cardRepository.findByOwnerIdAndNumberStartingWith(user.getId(), search, pageable);
    }




}
