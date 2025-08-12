package com.example.springdeploytest.book.service.response;

import com.example.springdeploytest.book.entity.Book;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class ListBookResponse {
    final private List<Book> bookList;
    final private Long totalItems;
    final private Integer totalPages;


    public List<Map<String, Object>> transformToResponseForm(){
        return bookList.stream()
                .map(book -> {
                    Map<String, Object> bookMap = new HashMap<>();
                    bookMap.put("id", book.getId());
                    bookMap.put("title", book.getTitle());
                    bookMap.put("price", book.getPrice());
                    bookMap.put("nickname", book.getAccount().getNickname());

                    return bookMap;
                })
                .collect(Collectors.toList());
    }
    public static ListBookResponse from(final Page<Book> paginatedBook){
        return new ListBookResponse(
                paginatedBook.getContent(),
                paginatedBook.getTotalElements(),
                paginatedBook.getTotalPages()
        );
    }
}
