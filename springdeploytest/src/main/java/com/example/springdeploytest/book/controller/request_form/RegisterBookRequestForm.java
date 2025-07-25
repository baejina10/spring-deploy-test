package com.example.springdeploytest.book.controller.request_form;

import com.example.springdeploytest.book.service.request.RegisterBookRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterBookRequestForm {
    final private String title;
    final private String content;
    final private String author;
    final private String publisher;
    final private String category;
    final private int price;

    public RegisterBookRequest toRegisterRequestBook(){
        return new RegisterBookRequest (title, content, author, publisher, category, price);
    }
}
