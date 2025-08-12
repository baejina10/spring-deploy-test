package com.example.springdeploytest.order.controller.request_form;

import com.example.springdeploytest.order.service.request.CreateOrderItemRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateOrderItemRequestForm {
    final private Long bookId;
    final private int quantity;

    public CreateOrderItemRequest toCreateOrderItemRequest() {
        return new CreateOrderItemRequest(bookId, quantity);
    }
}
