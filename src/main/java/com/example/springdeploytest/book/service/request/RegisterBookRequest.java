package com.example.springdeploytest.book.service.request;

import com.example.springdeploytest.account.entity.Account;
import com.example.springdeploytest.book.entity.Book;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterBookRequest {
    final private String title;
    final private String content;
    final private String author;
    final private String publisher;
    final private String category;
    final private int price;


    public Book toBook(Account account){
        return new Book(title, content, author, publisher, category, price, account);
    }
}
