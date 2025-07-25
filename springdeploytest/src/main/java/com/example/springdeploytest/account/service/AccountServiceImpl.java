package com.example.springdeploytest.account.service;

import com.example.springdeploytest.account.entity.Account;
import com.example.springdeploytest.account.repository.AccountRepository;
import com.example.springdeploytest.account.service.request.LoginAccountRequest;
import com.example.springdeploytest.account.service.request.RegisterAccountRequest;
import com.example.springdeploytest.account.service.response.LoginAccountResponse;
import com.example.springdeploytest.account.service.response.RegisterAccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    final private AccountRepository accountRepository;

    @Override
    public RegisterAccountResponse register(RegisterAccountRequest request) {

        Account requestAccount = request.toAccount();
        Account savedAccount = accountRepository.save(requestAccount);

        return RegisterAccountResponse.from(savedAccount);
    }

    @Override
    public LoginAccountResponse login(LoginAccountRequest request) {
        Account requestAccount = request.toLogin();
        Optional<Account> maybeAccount = accountRepository.findByEmail(requestAccount.getEmail());
        if (maybeAccount.isEmpty()) {
            throw new RuntimeException("account not found");
        }
        Account account = maybeAccount.get();

        return LoginAccountResponse.from(account);
    }
}
