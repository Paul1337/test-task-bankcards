package com.example.bankcards.service;

import com.example.bankcards.entity.User;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("cardAccessService")
public class CardAccessService {
    @Autowired
    private UserService userService;

    public boolean userOwnsCard(String cardNum) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(currentUsername);
        return user.getCards().stream().anyMatch(card -> card.getNumber().equals(cardNum));
    }
}
