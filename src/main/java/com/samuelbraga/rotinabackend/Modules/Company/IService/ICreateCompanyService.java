package com.samuelbraga.rotinabackend.Modules.Company.IService;

import com.samuelbraga.rotinabackend.Modules.Company.DTOS.CompanyDTO;
import com.samuelbraga.rotinabackend.Modules.Company.DTOS.CreateCompanyDTO;

public interface ICreateCompanyService {
  CompanyDTO execute(CreateCompanyDTO createCompanyDTO);
}
