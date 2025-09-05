package com.example.bankcards.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    private UUID id;

    @OneToMany(mappedBy = "owner")
    private List<Card> cards;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @Getter
    private Role role;

}
