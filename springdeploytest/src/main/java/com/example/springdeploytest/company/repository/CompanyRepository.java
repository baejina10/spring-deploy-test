package com.example.springdeploytest.company.repository;

import com.example.springdeploytest.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Long> {
}
