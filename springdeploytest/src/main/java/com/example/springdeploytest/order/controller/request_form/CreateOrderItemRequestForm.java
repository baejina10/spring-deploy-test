package com.example.springdeploytest.order.controller.request_form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateOrderItemRequestForm {
    final private Long bookId;
    final private int quantity;
    final private int price;

    public CreateOrderItemRequest toCreateOrderItemRequest() {
        return new CreateOrderItemRequest(bookId, quantity,price);
    }
}
