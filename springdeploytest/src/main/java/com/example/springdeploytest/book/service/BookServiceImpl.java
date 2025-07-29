package com.example.springdeploytest.book.service;

import com.example.springdeploytest.account.entity.Account;
import com.example.springdeploytest.account.repository.AccountRepository;
import com.example.springdeploytest.book.entity.Book;
import com.example.springdeploytest.book.repository.BookRepository;
import com.example.springdeploytest.book.service.request.ListBookRequest;
import com.example.springdeploytest.book.service.request.RegisterBookRequest;
import com.example.springdeploytest.book.service.response.ListBookResponse;
import com.example.springdeploytest.book.service.response.MyListBookResponse;
import com.example.springdeploytest.book.service.response.RegisterBookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
    public List<MyListBookResponse> myList(Long accountId) {
        List<Book> responseList = bookRepository.findAllByAccountId(accountId);
        return MyListBookResponse.from(responseList);
    }

    @Override
    public ListBookResponse list(ListBookRequest request) {
        // PageRequest 생성(시작은 0부터, 화면에 보여줄 땐 0이 좀 요상하니 1이라서 1 빼야함)
        PageRequest pageRequest = PageRequest.of(
                request.getPage() - 1, request.getPerPage());

        // 페이지 처리된 항목
        Page<Book> paginatedBook = bookRepository.findAllWithAccount(pageRequest);

        return ListBookResponse.from(paginatedBook);
    }
}
