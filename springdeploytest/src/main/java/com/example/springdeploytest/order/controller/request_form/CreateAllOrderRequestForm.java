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
        // orderItem은 리스트
        // 이러한 List 들은 stream() 을 사용할 수 있고
        // map을 통해 내부에 있는 정보들을 전부 순회 처리할 수 있음.
        // 순회하면서 mapped에 있는 요소 하나하나는 CreateOrderItemRequest 타입이므로
        // CreateOrderItemRequest 타입들이 사용할 수 있는
        // toCreateOrderItemRequest() 를 통해 RequestForm 타입으로 Request로 변환
        // collect() 를 통해 List 타입으로 만들어줌
        List<CreateOrderItemRequest> mapped = orderItems.stream()
                .map(CreateOrderItemRequestForm::toCreateOrderItemRequest)
                .collect(Collectors.toList());

        /*  위의 스트림 문장 for 문으로 했을 때
         *  for(int i =0; i<mapped.size(); i++){
         *      CreateOrderItemRequestForm mappedRequestForm = mapped.get(i);
         *      CreateOrderItemRequest mappedRequest = mappedRequestForm.toCreateOrderItemRequest();
         *      mapped.put(mappedRequest);
         *  }
         */
        return new CreateAllOrderItemRequest(mapped);
    }
}
