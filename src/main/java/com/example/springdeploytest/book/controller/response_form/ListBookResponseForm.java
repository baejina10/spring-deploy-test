package com.example.springdeploytest.book.controller.response_form;

import com.example.springdeploytest.book.service.response.ListBookResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ListBookResponseForm {
    final private List<Map<String, Object>> bookList;
    final private Long totalItems;
    final private Integer totalPages;

    public static ListBookResponseForm from(ListBookResponse response){
        List<Map<String, Object>> combinedBookList = response.transformToResponseForm();

        return new ListBookResponseForm(
                combinedBookList,
                response.getTotalItems(),
                response.getTotalPages()
        );
    }
}
