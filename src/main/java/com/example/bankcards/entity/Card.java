package com.example.bankcards.entity;

import com.example.bankcards.exception.InsufficientFundsException;
import io.jsonwebtoken.lang.Assert;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_DEFAULT)
    @JoinColumn(name = "owner_id", nullable = true)
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

    public enum Status {
        Active,
        Blocked,
        Expired
    }

    public Status getStatus() {
      if (isBlocked) return Status.Blocked;
      if (LocalDateTime.now().isAfter(expirationDateTime)) return Status.Expired;
      return Status.Active;
    }

    public void activate() {
      isBlocked = false;
    }

    public void block() {
      isBlocked = true;
    }

    private void withdraw(BigDecimal amount) {
        Assert.isTrue(amount.compareTo(BigDecimal.ZERO) > 0,"Amount must be positive");
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientFundsException(number);
        }
        balance = balance.subtract(amount);
    }

    private void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        balance = balance.add(amount);
    }

    public void transferCashFrom(Card anotherCard, BigDecimal amount) {
        this.deposit(amount);
        anotherCard.withdraw(amount);
    }

    public String getMaskedNumber() {
        return "*".repeat(12) + getNumber().substring(12, 16);
    }
}
