package com.example.springdeploytest.account.controller.request_form;

import com.example.springdeploytest.account.service.request.LoginAccountRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginAccountRequestForm {
    final private String email;
    final private String nickName;

    public LoginAccountRequest toLoginRequest(){
        return new LoginAccountRequest(email, nickName);
    }
}
