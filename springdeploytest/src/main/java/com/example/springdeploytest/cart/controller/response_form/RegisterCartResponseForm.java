package com.example.springdeploytest.cart.controller.response_form;

import com.example.springdeploytest.cart.service.response.RegisterCartResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class RegisterCartResponseForm {
    final private String message;
    final private String bookTitle;
    final private int quantity;

    public static RegisterCartResponseForm from(String message, RegisterCartResponse response){
        return new RegisterCartResponseForm(
                message,
                response.getBookTitle(),
                response.getQuantity()
        );
    }

}
