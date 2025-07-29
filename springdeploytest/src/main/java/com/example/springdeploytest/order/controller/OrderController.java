package com.example.springdeploytest.order.controller;

import com.example.springdeploytest.order.controller.request_form.CreateAllOrderRequestForm;
import com.example.springdeploytest.order.controller.request_form.OrderRequestForm;
import com.example.springdeploytest.order.controller.response_form.CartOrderResponseFrom;
import com.example.springdeploytest.order.controller.response_form.CreateAllOrderResponseForm;
import com.example.springdeploytest.order.controller.response_form.SingleOrderResponseForm;
import com.example.springdeploytest.order.service.OrderService;
import com.example.springdeploytest.order.service.request.CreateAllOrderItemRequest;
import com.example.springdeploytest.order.service.request.CreateAllOrderRequest;
import com.example.springdeploytest.order.service.request.OrderRequest;
import com.example.springdeploytest.order.service.response.CartOrderResponse;
import com.example.springdeploytest.order.service.response.CreateAllOrderResponse;
import com.example.springdeploytest.order.service.response.SingleOrderResponse;
import com.example.springdeploytest.redis_cache.RedisCacheService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    final private OrderService orderService;
    final private RedisCacheService redisCacheService;

    @PostMapping("/single-book")
    public SingleOrderResponseForm orderBook (
            @RequestHeader ("Authorization") String userToken,
            @RequestBody OrderRequestForm requestForm){

        String pureToken = extractToken(userToken);
        String accountId = redisCacheService.getValueByKey(pureToken,String.class);

        OrderRequest request = requestForm.toRequestOrder();
        SingleOrderResponse response = orderService.order(request, accountId);
        String message = "주문이 완료 되었습니다!";

        return SingleOrderResponseForm.form(message,response);

    }

    @PostMapping("/all-book")
    public CreateAllOrderResponseForm createAllOrder(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody CreateAllOrderRequestForm requestForm) {
        log.info("createAllOrder()");
        String pureToken = extractToken(authorizationHeader);
        Long accountId = redisCacheService.getValueByKey(pureToken,Long.class);

        CreateAllOrderRequest orderRequest = requestForm.toCreateAllOderRequest(accountId);
        CreateAllOrderItemRequest orderItemRequest = requestForm.toCreateAllOderItemRequest();
        CreateAllOrderResponse response = orderService.createAll(orderRequest, orderItemRequest);

        return CreateAllOrderResponseForm.from(response);

    }

    @PostMapping("/from-cart")
    public ResponseEntity<CartOrderResponseFrom> orderFromCart (@RequestHeader("Authorization") String userToken){
        String pureToken = extractToken(userToken);
        Long accountId = redisCacheService.getValueByKey(pureToken,Long.class);

        List<CartOrderResponse> response = orderService.orderFromCart(accountId);
        CartOrderResponseFrom result = CartOrderResponseFrom.from("주문이 완료 되었습니다!", response);

        return ResponseEntity.ok(result);
    }

    private String extractToken(String userToken){
        if (userToken != null && userToken.startsWith("Bearer ")){
            return userToken.substring(7);
        }
        return userToken;
    }

}
