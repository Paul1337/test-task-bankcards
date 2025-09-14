package com.example.bankcards.controller;

import com.example.bankcards.dto.cards.Transfer;
import com.example.bankcards.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards/transfer")
public class TransferController {
    @Autowired
    private TransferService transferService;

    @PostMapping({ "", "/" })
    @PreAuthorize("@cardAccessService.userOwnsCard(#dto.cardFrom) and @cardAccessService.userOwnsCard(#dto.cardTo)")
    public ResponseEntity<Void> transfer(@RequestBody Transfer.Request dto) {
        transferService.transferCash(dto.getCardFrom(), dto.getCardTo(), dto.getAmount());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
