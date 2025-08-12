package com.example.springdeploytest.account.controller.request_form;

import com.example.springdeploytest.account.service.request.RegisterAccountRequest;
import com.example.springdeploytest.account.service.response.RegisterAccountResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterAccountRequestForm {

    final private String email;
    final private String nickname;

    public RegisterAccountRequest toRegisterAccountRequest() {
        return new RegisterAccountRequest(email, nickname);
    }
}
