package com.example.springdeploytest.company.service.response;

import com.example.springdeploytest.company.entity.Company;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterCompanyResponse {
    final private String name;
    final private String email;

    public static RegisterCompanyResponse from(final Company company){
        return new RegisterCompanyResponse(
                company.getName(),
                company.getAccount().getEmail()

        );
    }
}
