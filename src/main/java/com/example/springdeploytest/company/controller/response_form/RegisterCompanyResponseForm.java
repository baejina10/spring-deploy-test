package com.example.springdeploytest.company.controller.response_form;

import com.example.springdeploytest.company.service.response.RegisterCompanyResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterCompanyResponseForm {
    final private String name;
    final private String email;

    public static RegisterCompanyResponseForm from(final RegisterCompanyResponse response){
        return new RegisterCompanyResponseForm(
                response.getName(),
                response.getEmail()
        );
    }
}
