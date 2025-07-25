package com.example.springdeploytest.order.service.response;

import com.example.springdeploytest.order.entity.Order;
import com.example.springdeploytest.order.entity.OrderItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CartOrderResponse {
    final private Long orderId;
    final private String nickName;
    final private String title;
    final private int bookPrice;
    final private int quantity;
    final private int totalPrice;

    public static List<CartOrderResponse> from(List<OrderItem> orderItem, List<Order> orders) {
        return orderItem.stream()
                .map(item ->{
                    Long orderId = item.getOrder().getId();

                    Order matchedOrder = orders.stream()
                            .filter(order -> order.getId().equals(orderId))
                            .findFirst()
                            .orElseThrow(()-> new IllegalArgumentException("주문번호가 일치하지 않습니다."));

                    return new CartOrderResponse(
                            matchedOrder.getId(),
                            matchedOrder.getAccount().getNickname(),
                            item.getBook().getTitle(),
                            item.getBook().getPrice(),
                            item.getQuantity(),
                            item.getItemTotalPrice()
                    );
                }).toList();
    }

}
