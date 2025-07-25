package com.example.springdeploytest.order.service;

import com.example.springdeploytest.order.service.request.OrderRequest;
import com.example.springdeploytest.order.service.response.CartOrderResponse;
import com.example.springdeploytest.order.service.response.SingleOrderResponse;

import java.util.List;

public interface OrderService {
    SingleOrderResponse order(OrderRequest request, String accountId);
    List<CartOrderResponse> orderFromCart(Long accountId);
}
