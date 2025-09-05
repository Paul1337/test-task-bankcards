package com.example.bankcards.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "card")
@NoArgsConstructor
public class Card {
    @Id
    @Column(name = "number", length = 16)
    @Getter
    private String number;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    @Getter
    private User owner;

    @Column(name = "expiration_date")
    @Getter
    private LocalDateTime expirationDateTime;

    @Getter
    @Column(name = "is_blocked")
    private boolean isBlocked;

    @Getter
    @Column(name = "balance")
    private BigDecimal balance;

    public Card(String number, User owner, LocalDateTime expirationDateTime) {
        this.number = number;
        this.owner = owner;
        this.expirationDateTime = expirationDateTime;
        isBlocked = false;
        balance = new BigDecimal(0);
    }

    public static enum Status {
        Active,
        Blocked,
        Expired
    }

    public Status getStatus() {
        return Status.Active;
    }
}
