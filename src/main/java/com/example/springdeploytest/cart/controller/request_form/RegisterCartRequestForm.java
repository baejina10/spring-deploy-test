package com.example.springdeploytest.cart.controller.request_form;

import com.example.springdeploytest.cart.service.request.RegisterCartRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterCartRequestForm {
    final private Long bookId;
    final private int quantity;

    public RegisterCartRequest toRequestCart() {
        return new RegisterCartRequest(bookId, quantity);
    }
}
