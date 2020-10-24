package com.samuelbraga.rotinabackend.Modules.Company.IService;

import com.samuelbraga.rotinabackend.Modules.Company.DTOS.CreateCompanyDTO;
import com.samuelbraga.rotinabackend.Modules.Company.Models.Company;

public interface ICreateCompanyService {
  Company execute(CreateCompanyDTO createCompanyDTO);
}
