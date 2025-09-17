package com.example.bankcards.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role")
@NoArgsConstructor
public class Role {
    @Id
    @Column(name = "\"value\"", length = 128)
    @Getter
    private String value;

    @OneToMany(mappedBy = "role")
    @Getter
    private List<User> users;

    public Role(String value) {
        this.value = value;
        this.users = new ArrayList<>();
    }
}


