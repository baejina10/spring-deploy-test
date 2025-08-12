package com.example.springdeploytest.order.service;

import com.example.springdeploytest.order.service.request.CreateAllOrderItemRequest;
import com.example.springdeploytest.order.service.request.CreateAllOrderRequest;
import com.example.springdeploytest.order.service.request.ListOrderRequest;
import com.example.springdeploytest.order.service.request.OrderRequest;
import com.example.springdeploytest.order.service.response.CartOrderResponse;
import com.example.springdeploytest.order.service.response.CreateAllOrderResponse;
import com.example.springdeploytest.order.service.response.ListOrderResponse;
import com.example.springdeploytest.order.service.response.SingleOrderResponse;

import java.util.List;

public interface OrderService {
    SingleOrderResponse order(OrderRequest request, String accountId);
    CreateAllOrderResponse createAll(CreateAllOrderRequest ordersRequest,
                                     CreateAllOrderItemRequest orderItemRequest);
    List<CartOrderResponse> orderFromCart(Long accountId);
    ListOrderResponse list(ListOrderRequest request);
}
