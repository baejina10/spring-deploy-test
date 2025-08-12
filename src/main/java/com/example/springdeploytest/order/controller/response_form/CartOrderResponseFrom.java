package com.example.springdeploytest.order.controller.response_form;

import com.example.springdeploytest.order.service.response.CartOrderResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CartOrderResponseFrom {
    final private String message;
    final private List<CartOrderResponse> cartOrderResponses;

    public static CartOrderResponseFrom from(String message, List<CartOrderResponse> cartOrderResponses) {
        return new CartOrderResponseFrom(message, cartOrderResponses);
    }

}
