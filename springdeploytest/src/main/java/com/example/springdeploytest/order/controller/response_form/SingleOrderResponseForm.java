package com.example.springdeploytest.order.controller.response_form;

import com.example.springdeploytest.order.service.response.SingleOrderResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SingleOrderResponseForm {
    final private String message;
    final private Long orderId;
    final private String nickName;
    final private String title;
    final private int bookPrice;
    final private int quantity;
    final private int totalPrice;


    public static SingleOrderResponseForm form(String message, SingleOrderResponse response){
        return new SingleOrderResponseForm(
                message,
                response.getOrderId(),
                response.getNickName(),
                response.getTitle(),
                response.getBookPrice(),
                response.getQuantity(),
                response.getTotalPrice()
        );
    }
}
