package com.example.springdeploytest.kakao_authentication.service.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KakaoUserInfoResponse {
    final private String email;
    final private String nickname;
    final private String accessToken;
    final private Boolean isNewUser;

    public static KakaoUserInfoResponse from(
            String email, String nickname, String accessToken, boolean isNewUser) {
        return new KakaoUserInfoResponse(
                email,
                nickname,
                accessToken,
                isNewUser
        );
    }
}
