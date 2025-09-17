package com.example.bankcards.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 128)
    private String name;

    @Column(length = 128, unique = true)
    private String username;

    @Column(length = 4096)
    private String password;

    @OneToMany(mappedBy = "owner")
    private List<Card> cards;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role")
    @Setter
    private Role role;

    public User(String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(getRole().getValue()));
    }
}
