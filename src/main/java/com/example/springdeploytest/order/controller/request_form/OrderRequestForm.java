package com.example.springdeploytest.order.controller.request_form;

import com.example.springdeploytest.order.service.request.OrderRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderRequestForm {
    final private int quantity;
    final private Long bookId;

    public OrderRequest toRequestOrder(){
        return new OrderRequest(quantity, bookId);
    }
}
