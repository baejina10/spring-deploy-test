package com.example.springdeploytest.cart.controller;

import com.example.springdeploytest.cart.controller.request_form.RegisterCartRequestForm;
import com.example.springdeploytest.cart.controller.response_form.ListCartResponseForm;
import com.example.springdeploytest.cart.controller.response_form.RegisterCartResponseForm;
import com.example.springdeploytest.cart.entity.Cart;
import com.example.springdeploytest.cart.service.CartService;
import com.example.springdeploytest.cart.service.request.RegisterCartRequest;
import com.example.springdeploytest.cart.service.response.RegisterCartResponse;
import com.example.springdeploytest.redis_cache.RedisCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    final private CartService cartService;
    final private RedisCacheService redisCacheService;

    @PostMapping("/register")
    public RegisterCartResponseForm registerCart(
            @RequestHeader ("Authorization") String userToken,
            @RequestBody RegisterCartRequestForm requestForm){
        log.info("registerCart() -> requestForm: {}", requestForm);

        String pureToken = userToken.replace("Bearer", "").trim();
        Long accountId = redisCacheService.getValueByKey(pureToken, Long.class);

        RegisterCartRequest request = requestForm.toRequestCart();
        RegisterCartResponse response = cartService.register(request, accountId);

        String message = "상품이 장바구니에 담겼습니다.";

        return RegisterCartResponseForm.from(message, response);

    }

    @GetMapping("/list")
    public ResponseEntity<ListCartResponseForm> cartList(@RequestHeader ("Authorization") String userToken){
        String pureToken = userToken.replace("Bearer", "").trim();
        Long accountId = redisCacheService.getValueByKey(pureToken, Long.class);

        List<RegisterCartResponse> allList = cartService.list(accountId);
        ListCartResponseForm result = ListCartResponseForm.from("장바구니 목록", allList);

        return ResponseEntity.ok(result);
    }

}
