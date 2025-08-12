package com.example.springdeploytest.account.controller.response_form;

import com.example.springdeploytest.account.service.response.RegisterAccountResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterAccountResponseForm {
    final private String userToken;
    final private String email;
    final private String nickname;

    public static RegisterAccountResponseForm from(final RegisterAccountResponse response, String userToken) {
        return new RegisterAccountResponseForm(
                userToken,
                response.getEmail(),
                response.getNickname()
        );
    }
}
