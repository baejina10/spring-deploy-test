package com.example.springdeploytest.order.controller.request_form;

import com.example.springdeploytest.order.controller.response_form.CreateAllOrderResponseForm;
import com.example.springdeploytest.order.service.request.CreateAllOrderItemRequest;
import com.example.springdeploytest.order.service.request.CreateAllOrderRequest;
import com.example.springdeploytest.order.service.request.CreateOrderItemRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class CreateAllOrderRequestForm {

    final private List<CreateOrderItemRequestForm> orderItems;

    public CreateAllOrderRequest toCreateAllOderRequest(Long accountId){
        return new CreateAllOrderRequest(accountId);
    }

    public CreateAllOrderItemRequest toCreateAllOderItemRequest(){
        List<CreateOrderItemRequest> mapped = orderItems.stream()
                .map(CreateOrderItemRequestForm::toCreateOrderItemRequest)
                .collect(Collectors.toList());

        return new CreateAllOrderItemRequest(mapped);
    }
}
