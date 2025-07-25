package com.example.springdeploytest.cart.service;

import com.example.springdeploytest.cart.controller.response_form.RegisterCartResponseForm;
import com.example.springdeploytest.cart.service.request.RegisterCartRequest;
import com.example.springdeploytest.cart.service.response.RegisterCartResponse;

import java.util.List;

public interface CartService {
    RegisterCartResponse register(RegisterCartRequest request, Long accountId);
    List<RegisterCartResponse> list(Long accountId);
}
