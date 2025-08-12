package com.example.springdeploytest.cart.service.response;

import com.example.springdeploytest.book.entity.Book;
import com.example.springdeploytest.cart.controller.response_form.RegisterCartResponseForm;
import com.example.springdeploytest.cart.entity.Cart;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class RegisterCartResponse {
    final private String bookTitle;
    final private int quantity;

    public static RegisterCartResponse form(Cart cart){
        return new RegisterCartResponse(
                cart.getBook().getTitle(),
                cart.getQuantity()
        );
    }
    public static List<RegisterCartResponse> form(List<Cart> cartList){
        return cartList.stream()
                .map(cart-> new RegisterCartResponse(
                        cart.getBook().getTitle(),
                        cart.getQuantity()
                ))
                .toList();
    }
}
