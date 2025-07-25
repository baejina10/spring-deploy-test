package com.example.springdeploytest.book.service;

import com.example.springdeploytest.account.entity.Account;
import com.example.springdeploytest.account.repository.AccountRepository;
import com.example.springdeploytest.book.entity.Book;
import com.example.springdeploytest.book.repository.BookRepository;
import com.example.springdeploytest.book.service.request.RegisterBookRequest;
import com.example.springdeploytest.book.service.response.ListBookResponse;
import com.example.springdeploytest.book.service.response.RegisterBookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    final private BookRepository bookRepository;
    final private AccountRepository accountRepository;

    @Override
    public RegisterBookResponse register(
            RegisterBookRequest request, String accountId) {

        Account account = accountRepository.findById(Long.parseLong(accountId))
                .orElseThrow(()-> new RuntimeException("account not found"));

        Book requestBook = request.toBook(account);

        Book savedBook = bookRepository.save(requestBook);
        return RegisterBookResponse.form(savedBook);
    }

    @Override
    public List<ListBookResponse> list(Long accountId) {
        List<Book> responseList = bookRepository.findAllByAccountId(accountId);
        return ListBookResponse.from(responseList);
    }
}
