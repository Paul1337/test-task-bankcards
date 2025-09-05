package com.example.bankcards.controller;

import com.example.bankcards.dto.CreateCard;
import com.example.bankcards.dto.GetCard;
import com.example.bankcards.entity.Card;
import com.example.bankcards.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {
    @Autowired
    private CardService cardService;

    @PostMapping("/")
    public void createCard(@RequestBody CreateCard.Request dto) {
        cardService.createCard(dto);
    }

    @GetMapping("/test")
    public String test() {
        return "test40";
    }

    @GetMapping("/all")
    public ResponseEntity<List<GetCard.ShallowResponse>> getAllCards() {
        // admin role
        return ResponseEntity.status(200).body(List.of());
    }

    @PostMapping("/block/{cardNum}")
    public void blockCard(@PathVariable(name = "cardNum") String cardNum) {

    }

    @PostMapping("/activate/{cardNum}")
    public void activateCard(@PathVariable(name = "cardNum") String cardNum) {

    }

    @PostMapping("/delete/{cardNumber}")
    public void deleteCard(@PathVariable(name = "cardNum") String cardNum) {

    }
}
