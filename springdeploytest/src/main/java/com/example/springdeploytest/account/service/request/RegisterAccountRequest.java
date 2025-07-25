package com.example.springdeploytest.account.service.request;

import com.example.springdeploytest.account.entity.Account;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterAccountRequest {
    final private String email;
    final private String nickname;

    public Account toAccount() {
        return new Account(email, nickname);
    }

}
