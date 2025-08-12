package com.example.springdeploytest.order.controller.response_form;

import com.example.springdeploytest.order.controller.request_form.CreateOrderItemRequestForm;
import com.example.springdeploytest.order.service.response.CreateAllOrderResponse;
import com.example.springdeploytest.order.service.response.CreateOrderItemResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CreateAllOrderResponseForm {
    final private Long orderId;
    final private Integer itemCount;
    final private List<CreateOrderItemResponse> itemsList;

    public static CreateAllOrderResponseForm from(final CreateAllOrderResponse response){
        return new CreateAllOrderResponseForm(
                response.getOrderId(),
                response.getItemCount(),
                response.getItemList()
        );
    }
}
