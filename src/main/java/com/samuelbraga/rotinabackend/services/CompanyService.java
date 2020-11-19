package com.samuelbraga.rotinabackend.services;

import com.samuelbraga.rotinabackend.dtos.company.CompanyDTO;
import com.samuelbraga.rotinabackend.dtos.company.CreateCompanyDTO;

import java.util.UUID;

public interface CompanyService {
  CompanyDTO createCompany(CreateCompanyDTO createCompanyDTO, UUID userId);
}
