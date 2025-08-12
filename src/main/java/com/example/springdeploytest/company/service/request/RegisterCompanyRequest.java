package com.example.springdeploytest.company.service.request;

import com.example.springdeploytest.account.entity.Account;
import com.example.springdeploytest.company.entity.Company;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterCompanyRequest {
    final private String name;
    final private Long accountId;

    public Company toCompany(Account account){
        return new Company(name, account);
    }
}
