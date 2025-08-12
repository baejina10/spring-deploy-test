package com.example.springdeploytest.company.controller.request_form;

import com.example.springdeploytest.company.service.request.RegisterCompanyRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterCompanyRequestForm {
    final private String name;

    public RegisterCompanyRequest toRegisterCompanyRequest(Long accountId){
        return new RegisterCompanyRequest(name, accountId);
    }
}
