package com.example.springdeploytest.company.service;

import com.example.springdeploytest.account.entity.Account;
import com.example.springdeploytest.account.repository.AccountRepository;
import com.example.springdeploytest.company.entity.Company;
import com.example.springdeploytest.company.repository.CompanyRepository;
import com.example.springdeploytest.company.service.request.RegisterCompanyRequest;
import com.example.springdeploytest.company.service.response.RegisterCompanyResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CompanyServiceImpl implements CompanyService {
    final private CompanyRepository companyRepository;
    final private AccountRepository accountRepository;

    @Override
    public RegisterCompanyResponse register(RegisterCompanyRequest request) {

        Long accountId = request.getAccountId();
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("account not found"));

        Company requestCompany = request.toCompany(account);
        Company savedCompany = companyRepository.save(requestCompany);


        return RegisterCompanyResponse.from(savedCompany);
    }
}
