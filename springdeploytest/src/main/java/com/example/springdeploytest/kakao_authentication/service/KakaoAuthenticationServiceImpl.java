package com.example.springdeploytest.kakao_authentication.service;

import com.example.springdeploytest.account.entity.Account;
import com.example.springdeploytest.account.repository.AccountRepository;
import com.example.springdeploytest.kakao_authentication.controller.response_form.KakaoUserInfoResponseForm;
import com.example.springdeploytest.kakao_authentication.repository.KakaoAuthenticationRepository;
import com.example.springdeploytest.kakao_authentication.service.response.KakaoUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoAuthenticationServiceImpl implements KakaoAuthenticationService {

    final private KakaoAuthenticationRepository kakaoAuthenticationRepository;
    final private AccountRepository accountRepository;

    @Override
    public KakaoUserInfoResponse handleLogin(String code) {
        // Map<String, Object> getAccessToken(String code);
        Map<String, Object> tokenResponse = kakaoAuthenticationRepository.getAccessToken(code);
        String accessToken = tokenResponse.get("access_token").toString();

        // Map<String, Object> getUserInfo(String accessToken);
        Map<String, Object> userInfoResponse = kakaoAuthenticationRepository.getUserInfo(accessToken);
        Map<?,?> userInfoProperties = (Map<?,?>)userInfoResponse.get("properties");
        String nickName = (String) (userInfoProperties).get("nickname");

        Map<?,?> userInfoKakaoAccount = (Map<?,?>)userInfoResponse.get("kakao_account");
        String email = (String) (userInfoKakaoAccount).get("email");

        Optional<Account> maybeAccount = accountRepository.findByEmail(email);
        boolean isNewUser = false;

        if (maybeAccount.isEmpty()) {
            isNewUser = true;
        }

        return KakaoUserInfoResponse.from(email, nickName, accessToken, isNewUser);
    }
}
