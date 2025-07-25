package com.example.springdeploytest.account.service;

import com.example.springdeploytest.account.service.request.LoginAccountRequest;
import com.example.springdeploytest.account.service.request.RegisterAccountRequest;
import com.example.springdeploytest.account.service.response.LoginAccountResponse;
import com.example.springdeploytest.account.service.response.RegisterAccountResponse;

public interface AccountService {
    RegisterAccountResponse register(RegisterAccountRequest request);
    LoginAccountResponse login(LoginAccountRequest request);
}
