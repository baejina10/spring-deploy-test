package com.example.springdeploytest.cart.entity;

import com.example.springdeploytest.account.entity.Account;
import com.example.springdeploytest.book.entity.Book;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    public Cart(int quantity, Account account, Book book) {
        this.quantity = quantity;
        this.account = account;
        this.book = book;
    }
}
