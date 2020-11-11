package com.samuelbraga.rotinabackend.modules.company.iservice;

import com.samuelbraga.rotinabackend.modules.company.dtos.CompanyDTO;
import com.samuelbraga.rotinabackend.modules.company.dtos.CreateCompanyDTO;

import java.util.UUID;

public interface ICreateCompanyService {
  CompanyDTO execute(CreateCompanyDTO createCompanyDTO, UUID userId);
}
