package com.example.springdeploytest.order.controller.request_form;

import com.example.springdeploytest.order.entity.OrderItem;
import com.example.springdeploytest.order.service.request.ListOrderRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@RequiredArgsConstructor
@ToString
public class ListOrderRequestForm {
    final private Integer page;
    final private Integer perPage;

    public ListOrderRequest toListOrderRequest(Long accountId){
        return new ListOrderRequest(page,perPage,accountId);
    }
}
