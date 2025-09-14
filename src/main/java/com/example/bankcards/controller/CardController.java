package com.example.bankcards.controller;

import com.example.bankcards.dto.PageResponse;
import com.example.bankcards.dto.cards.CreateCard;
import com.example.bankcards.dto.cards.GetCard;
import com.example.bankcards.dto.cards.GetCardList;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.security.annotations.RoleAdmin;
import com.example.bankcards.security.annotations.RoleUser;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {
    @Autowired
    private CardService cardService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    @RoleAdmin
    public ResponseEntity<GetCard.FullResponse> createCard(@RequestBody @Valid CreateCard.Request dto) {
        var newCard = cardService.createCard(dto);
        var cardResponse = modelMapper.map(newCard, GetCard.FullResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(cardResponse);
    }

    @GetMapping({ "/all" })
    @RoleAdmin
    public ResponseEntity<PageResponse<GetCard.ShallowResponse>> getAllCards(GetCardList.Request dto) {
        var allCardsPage = cardService.getAllCards(PageRequest.of(dto.getPage(), dto.getSize()));
        var response = PageResponse.of(allCardsPage.map(card -> modelMapper.map(card, GetCard.ShallowResponse.class)));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/my")
    @RoleUser
    public ResponseEntity<PageResponse<GetCard.FullResponse>> getMyCards(GetCardList.Request dto, @AuthenticationPrincipal String username) {
        var cardsPage = cardService.getCardsByUsername(username, PageRequest.of(dto.getPage(), dto.getSize()), dto.getSearch());
        var response = PageResponse.of(cardsPage.map(card -> modelMapper.map(card, GetCard.FullResponse.class)));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("{cardNum}/block")
    @PreAuthorize("hasRole('ADMIN') or @cardSecurity.isOwner(#cardNum, authentication.name)")
    public ResponseEntity<Void> blockCard(@PathVariable(name = "cardNum") String cardNum) {
        cardService.blockCard(cardNum);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("{cardNum}/activate")
    @PreAuthorize("isAuthenticated() and @cardSecurity.isOwner(#cardNum, authentication.name)")
    public ResponseEntity<Void> activateCard(@PathVariable(name = "cardNum") String cardNum) {
        cardService.activateCard(cardNum);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("{cardNum}/delete")
    @RoleAdmin
    public ResponseEntity<Void> deleteCard(@PathVariable(name = "cardNum") String cardNum) {
        cardService.deleteCard(cardNum);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
