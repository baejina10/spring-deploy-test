package com.example.springdeploytest.account.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "social_account")
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String email;
    String nickname;

    public Account(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }
}
