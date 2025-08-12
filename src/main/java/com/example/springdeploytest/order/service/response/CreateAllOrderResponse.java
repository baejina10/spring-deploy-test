package com.example.springdeploytest.order.service.response;

import com.example.springdeploytest.order.entity.Order;
import com.example.springdeploytest.order.entity.OrderItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class CreateAllOrderResponse {
    final private Long orderId;
    final private Integer itemCount;
    final private List<CreateOrderItemResponse> itemList;

    public static CreateAllOrderResponse from(
            final Order order, List<OrderItem> savedOrderItemList) {
        List<CreateOrderItemResponse> itemResponsesList = savedOrderItemList.stream()
                .map(CreateOrderItemResponse::from)
                .collect(Collectors.toList());
        return new CreateAllOrderResponse(
                order.getId(),
                itemResponsesList.size(),
                itemResponsesList
        );
    }

}
