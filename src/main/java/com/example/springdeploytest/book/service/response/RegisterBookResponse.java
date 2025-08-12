package com.example.springdeploytest.book.service.response;

import com.example.springdeploytest.book.entity.Book;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterBookResponse {
    final private Long bookId;
    final private String title;
    final private String content;
    final private String author;
    final private String publisher;
    final private String category;
    final private int price;

    public static RegisterBookResponse form(Book book){
        return new RegisterBookResponse(
                book.getId(),
                book.getTitle(),
                book.getContent(),
                book.getAuthor(),
                book.getPublisher(),
                book.getCategory(),
                book.getPrice()
        );
    }
}
