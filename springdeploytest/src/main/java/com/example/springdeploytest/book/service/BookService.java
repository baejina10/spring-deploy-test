package com.example.springdeploytest.book.service;

import com.example.springdeploytest.book.service.request.RegisterBookRequest;
import com.example.springdeploytest.book.service.response.ListBookResponse;
import com.example.springdeploytest.book.service.response.RegisterBookResponse;

import java.util.List;

public interface BookService {
    RegisterBookResponse register(RegisterBookRequest request, String accountId);
    List<ListBookResponse> list(Long accountId);
}
