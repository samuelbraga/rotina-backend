package com.samuelbraga.rotinabackend.iservices;

import com.samuelbraga.rotinabackend.dtos.company.CompanyDTO;
import com.samuelbraga.rotinabackend.dtos.company.CreateCompanyDTO;

import java.util.UUID;

public interface ICompanyService {
  CompanyDTO createCompany(CreateCompanyDTO createCompanyDTO, UUID userId);
}
