package com.example.springdeploytest.account.controller;

import com.example.springdeploytest.account.controller.request_form.LoginAccountRequestForm;
import com.example.springdeploytest.account.controller.request_form.RegisterAccountRequestForm;
import com.example.springdeploytest.account.controller.response_form.LoginAccountResponseForm;
import com.example.springdeploytest.account.controller.response_form.RegisterAccountResponseForm;
import com.example.springdeploytest.account.service.AccountService;
import com.example.springdeploytest.account.service.request.LoginAccountRequest;
import com.example.springdeploytest.account.service.request.RegisterAccountRequest;
import com.example.springdeploytest.account.service.response.LoginAccountResponse;
import com.example.springdeploytest.account.service.response.RegisterAccountResponse;
import com.example.springdeploytest.redis_cache.RedisCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    final private AccountService accountService;
    final private RedisCacheService redisCacheService;

    @PostMapping("/register")
    public RegisterAccountResponseForm createAccount (
            @RequestHeader ("Authorization") String authorizeHeader,
            @RequestBody RegisterAccountRequestForm requestForm) {
        log.info("register() -> requestForm: {}", requestForm);

        // 지금 가입 요청한 사용자가 정상적인 임시토큰을 가지고 있는가?
        String temporaryUserToken = authorizeHeader.replace("Bearer ", "").trim();
        String accessToken = redisCacheService.getValueByKey(temporaryUserToken, String.class);

        if (accessToken == null) {
            throw new IllegalArgumentException("만료되었거나 잘못된 토큰 요청입니다");
        }

        // 회원 가입 진행
        RegisterAccountRequest request = requestForm.toRegisterAccountRequest();
        RegisterAccountResponse response = accountService.register(request);
        Long accountId = response.getAccountId();

        // 찐 토큰 발급
        String userToken = UUID.randomUUID().toString();
        redisCacheService.setKeyAndValue(userToken, accountId);
        redisCacheService.setKeyAndValue(accountId, accessToken);

        // 임시 토큰 삭제
        redisCacheService.deleteByKey(temporaryUserToken);

        return RegisterAccountResponseForm.from(response, userToken);
    }

    @PostMapping("/login")
    public LoginAccountResponseForm login (
            @RequestHeader("Authorization") String authorizeHeader,
            @RequestBody LoginAccountRequestForm requestForm){
        // 사용자가 맞는 임시 토큰을 가지고 있는지 확인
        String temporaryUserToken = authorizeHeader.replace("Bearer ", "").trim();
        String accessToken = redisCacheService.getValueByKey(temporaryUserToken, String.class);

        if (accessToken == null) {
            throw new IllegalArgumentException("잘못 되었거나 만료된 토큰입니다.");
        }
        // 로그인 과정
        // LoginAccountRequestForm 요청한 정보를 서비스에 있는 LoginAccountRequest 에 전달해 객체 생성
        LoginAccountRequest request = requestForm.toLoginRequest();
        // LoginAccountRepose 객체를 생성해서 서비스에 있는 로그인 비지니스 로직이 반환하는 값을 담는다.
        LoginAccountResponse response = accountService.login(request);
        Long accountId = response.getAccountId();

        // 찐 토큰 발급
        String userToken = UUID.randomUUID().toString();
        redisCacheService.setKeyAndValue(userToken, accountId);
        redisCacheService.setKeyAndValue(accountId, accessToken);

        // 임시 토큰 삭제
        redisCacheService.deleteByKey(temporaryUserToken);

        return LoginAccountResponseForm.from("로그인에 성공하였습니다!", userToken);

    }

}
