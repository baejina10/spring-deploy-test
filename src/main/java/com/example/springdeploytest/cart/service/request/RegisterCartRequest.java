package com.example.springdeploytest.cart.service.request;

import com.example.springdeploytest.account.entity.Account;
import com.example.springdeploytest.book.entity.Book;
import com.example.springdeploytest.cart.entity.Cart;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterCartRequest {
    final private Long bookId;
    final private int quantity;

    public Cart toCart(Book book, Account account) {
        return new Cart(quantity, account, book);
    }

}
