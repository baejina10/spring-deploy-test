package com.example.springdeploytest.book.controller.response_form;

import com.example.springdeploytest.book.service.response.MyListBookResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class MyListBookResponseForm {
    final private String message;
    final private List<MyListBookResponse> bookList;

    public static MyListBookResponseForm from (String message, List<MyListBookResponse> bookList){
        return new MyListBookResponseForm(message, bookList);
    }
}
