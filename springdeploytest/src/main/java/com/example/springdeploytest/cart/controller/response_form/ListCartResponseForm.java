package com.example.springdeploytest.cart.controller.response_form;

import com.example.springdeploytest.cart.service.response.RegisterCartResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ListCartResponseForm {
    final private String message;
    final private List<RegisterCartResponse> cartList;

    public static ListCartResponseForm from(String message, List<RegisterCartResponse> responseList) {
        return new ListCartResponseForm(message, responseList);
    }
}
