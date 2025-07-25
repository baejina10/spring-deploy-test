package com.example.springdeploytest.account.controller.response_form;

import com.example.springdeploytest.account.entity.Account;
import com.example.springdeploytest.account.service.response.LoginAccountResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginAccountResponseForm {
    final private String message;
    final private String userToken;

    public static  LoginAccountResponseForm from(String message, String userToken) {
        return new LoginAccountResponseForm(message, userToken);
    }
}
