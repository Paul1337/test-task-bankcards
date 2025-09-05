package com.example.bankcards.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @Column(name = "value", length = 128)
    @Getter
    private String value;

    @OneToMany(mappedBy = "role")
    @Getter
    private List<User> users;
}


