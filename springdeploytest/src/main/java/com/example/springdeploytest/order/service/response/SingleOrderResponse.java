package com.example.springdeploytest.order.service.response;

import com.example.springdeploytest.order.entity.Order;
import com.example.springdeploytest.order.entity.OrderItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SingleOrderResponse {
    final private Long orderId;
    final private String nickName;
    final private String title;
    final private int bookPrice;
    final private int quantity;
    final private int totalPrice;

    public static SingleOrderResponse from(OrderItem orderItem, Order order) {
        return new SingleOrderResponse(
                order.getId(),
                order.getAccount().getNickname(),
                orderItem.getBook().getTitle(),
                orderItem.getBook().getPrice(),
                orderItem.getQuantity(),
                order.getTotalPrice()
        );
    }
}
