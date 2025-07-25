package com.example.springdeploytest.kakao_authentication.controller.response_form;

import com.example.springdeploytest.kakao_authentication.service.response.KakaoUserInfoResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KakaoUserInfoResponseForm {

    final private String email;
    final private String nickName;
    final private String accessToken;

    public static KakaoUserInfoResponseForm from(KakaoUserInfoResponse kakaoUserInfoResponse) {
        return new KakaoUserInfoResponseForm(
                kakaoUserInfoResponse.getEmail(),
                kakaoUserInfoResponse.getNickname(),
                kakaoUserInfoResponse.getAccessToken()
        );
    }

}
