package com.example.springdeploytest.kakao_authentication.controller.response_form;

import com.example.springdeploytest.kakao_authentication.service.response.KakaoUserInfoResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KakaoLoginResponseForm {
    final private String email;
    final private String nickname;
    final private String userToken;
    final private Boolean isNewUser;

    public static KakaoLoginResponseForm from(
            KakaoUserInfoResponse kakaoUserInfoResponse,
            String temporaryUserToken) {

        return new KakaoLoginResponseForm(
                kakaoUserInfoResponse.getEmail(),
                kakaoUserInfoResponse.getNickname(),
                temporaryUserToken,
                kakaoUserInfoResponse.getIsNewUser()
        );
    }
}
