package com.example.springdeploytest.kakao_authentication.controller.request_form;

import com.example.springdeploytest.account.service.request.RegisterAccountRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KakaoLoginRequestForm {

    final private String email;
    final private String nickname;

    public RegisterAccountRequest toRegisterAccountRequest() {
        return new RegisterAccountRequest(email, nickname);
    }
}
