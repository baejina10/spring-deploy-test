package com.example.springdeploytest.order.service.request;

import com.example.springdeploytest.account.entity.Account;
import com.example.springdeploytest.order.entity.Order;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateAllOrderRequest {
    final private Long accountId;

    public Order toOrder(Account account) {
        return new Order(account);
    }
}
