package com.example.springdeploytest.cart.service;

import com.example.springdeploytest.account.entity.Account;
import com.example.springdeploytest.account.repository.AccountRepository;
import com.example.springdeploytest.book.entity.Book;
import com.example.springdeploytest.book.repository.BookRepository;
import com.example.springdeploytest.cart.entity.Cart;
import com.example.springdeploytest.cart.repository.CartRepository;
import com.example.springdeploytest.cart.service.request.RegisterCartRequest;
import com.example.springdeploytest.cart.service.response.RegisterCartResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    final private CartRepository cartRepository;
    final private BookRepository bookRepository;
    final private AccountRepository accountRepository;

    @Override
    public RegisterCartResponse register(RegisterCartRequest request, Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("account not found"));

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("book not found"));

        Cart requestCart = request.toCart(book, account);
        Cart savedCart = cartRepository.save(requestCart);

        return RegisterCartResponse.form(savedCart);
    }

    @Override
    public List<RegisterCartResponse> list(Long accountId) {
        List<Cart> findAll = cartRepository.findAllByAccountId(accountId);
        return RegisterCartResponse.form(findAll);
    }
}
