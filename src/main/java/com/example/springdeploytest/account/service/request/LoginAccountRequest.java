package com.example.springdeploytest.account.service.request;

import com.example.springdeploytest.account.entity.Account;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginAccountRequest {
    final private String email;
    final private String nickName;

    public Account toLogin(){
        return new Account(email, nickName);
    }
}
