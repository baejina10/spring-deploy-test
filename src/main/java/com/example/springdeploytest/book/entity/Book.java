package com.example.springdeploytest.book.entity;

import com.example.springdeploytest.account.entity.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String author;
    private String publisher;
    private String category;
    private int price;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    public Book(String title, String content, String author, String publisher, String category, int price, Account account) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.publisher = publisher;
        this.category = category;
        this.price = price;
        this.account = account;
    }
}
