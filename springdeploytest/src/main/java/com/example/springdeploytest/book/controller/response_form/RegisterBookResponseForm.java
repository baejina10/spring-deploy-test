package com.example.springdeploytest.book.controller.response_form;

import com.example.springdeploytest.book.service.response.RegisterBookResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterBookResponseForm {
    final private String message;
    final private Long bookId;
    final private String title;
    final private String content;
    final private String author;
    final private String publisher;
    final private String category;
    final private int price;

    public static RegisterBookResponseForm form(String message, RegisterBookResponse response){
        return new RegisterBookResponseForm(
                message,
                response.getBookId(),
                response.getTitle(),
                response.getContent(),
                response.getAuthor(),
                response.getPublisher(),
                response.getCategory(),
                response.getPrice()

        );
    }
}
