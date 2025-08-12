package com.example.springdeploytest.book.controller.request_form;

import com.example.springdeploytest.book.service.request.ListBookRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class ListBookRequestForm {
    final private Integer page;
    final private Integer perPage;

    public ListBookRequest toListBookRequest() {
        return new ListBookRequest(page,perPage);
    }
}
