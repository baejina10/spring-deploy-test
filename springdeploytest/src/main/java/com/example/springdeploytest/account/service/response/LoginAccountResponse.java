package com.example.springdeploytest.account.service.response;

import com.example.springdeploytest.account.entity.Account;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginAccountResponse {
    final private Long accountId;
    final private String email;
    final private String nickname;

    public static LoginAccountResponse from(final Account account) {
        return new LoginAccountResponse(
                account.getId(),
                account.getEmail(),
                account.getNickname());
    }
}
