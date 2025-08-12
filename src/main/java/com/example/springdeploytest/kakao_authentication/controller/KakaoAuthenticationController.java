package com.example.springdeploytest.kakao_authentication.controller;

import com.example.springdeploytest.account.controller.AccountController;
import com.example.springdeploytest.account.entity.Account;
import com.example.springdeploytest.account.repository.AccountRepository;
import com.example.springdeploytest.account.service.AccountService;
import com.example.springdeploytest.account.service.request.RegisterAccountRequest;
import com.example.springdeploytest.kakao_authentication.controller.response_form.KakaoLoginResponseForm;
import com.example.springdeploytest.kakao_authentication.service.KakaoAuthenticationService;
import com.example.springdeploytest.kakao_authentication.service.response.KakaoUserInfoResponse;
import com.example.springdeploytest.redis_cache.RedisCacheService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/kakao-authentication")
public class KakaoAuthenticationController {

    final private KakaoAuthenticationService kakaoAuthenticationService;
    private final RedisCacheService redisCacheService;
    final private AccountRepository accountRepository;
    final private AccountService accountService;

    @GetMapping("/login")
    public KakaoLoginResponseForm requestLogin(@RequestParam ("code") String code,
                                               HttpServletRequest request) throws IOException {
        log.info("requestLogin(): code {}", code);

        KakaoUserInfoResponse response = kakaoAuthenticationService.handleLogin(code);
        String accessToken = response.getAccessToken();
        String temporaryUserToken = createTemporaryUserToken(accessToken);

        return KakaoLoginResponseForm.from(response, temporaryUserToken);
    }

    private String createTemporaryUserToken(String accessToken) {
        try{
            String temporaryUserToken = UUID.randomUUID().toString();
            redisCacheService.setKeyAndValue(temporaryUserToken, accessToken, Duration.ofMinutes(5));
            return temporaryUserToken;
        } catch (Exception e){
            throw new RuntimeException("Redis Token 생성 중 에러 발생: "+ e.getMessage());
        }
    }
}
