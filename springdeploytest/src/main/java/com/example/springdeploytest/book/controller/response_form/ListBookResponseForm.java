package com.example.springdeploytest.book.controller.response_form;

import com.example.springdeploytest.book.service.response.ListBookResponse;
import com.example.springdeploytest.cart.service.response.RegisterCartResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ListBookResponseForm {
    final private String message;
    final private List<ListBookResponse> bookList;

    public static ListBookResponseForm from (String message, List<ListBookResponse> bookList){
        return new ListBookResponseForm(message, bookList);
    }
}
