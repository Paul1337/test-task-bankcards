package com.example.bankcards.service;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Component("cardSecurity")
public class CardSecurity {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean isOwner(String cardNum, String username) {
        Optional<Card> cardOpt = cardRepository.findById(cardNum);
        if (cardOpt.isEmpty()) return false;
        Card card = cardOpt.get();
        User owner = card.getOwner();
        return owner != null && owner.getUsername().equals(username);
    }
}
