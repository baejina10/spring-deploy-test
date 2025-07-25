package com.example.springdeploytest.kakao_authentication.service;

import com.example.springdeploytest.kakao_authentication.service.response.KakaoUserInfoResponse;

public interface KakaoAuthenticationService {
    KakaoUserInfoResponse handleLogin(String code);
}
