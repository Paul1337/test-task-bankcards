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

@Entity
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    private UUID id;

    @Column(length = 128)
    @Getter
    private String name;

    @Column(length = 128, unique = true)
    @Getter
    private String username;

    @Column(length = 4096)
    @Getter
    private String password;

    @OneToMany(mappedBy = "owner")
    @Getter
    private List<Card> cards;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role")
    @Getter
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
