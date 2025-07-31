package com.example.springdeploytest.company.service;


import com.example.springdeploytest.company.service.request.RegisterCompanyRequest;
import com.example.springdeploytest.company.service.response.RegisterCompanyResponse;

public interface CompanyService {
    public RegisterCompanyResponse register(RegisterCompanyRequest request);
}
