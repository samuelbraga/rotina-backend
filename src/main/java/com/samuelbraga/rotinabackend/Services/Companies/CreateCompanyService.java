package com.samuelbraga.rotinabackend.Services.Companies;

import com.samuelbraga.rotinabackend.DTO.Companies.CreateCompanyDTO;
import com.samuelbraga.rotinabackend.Models.Company;
import com.samuelbraga.rotinabackend.Repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyService {
  
  private final CompanyRepository companyRepository;
  
  @Autowired
  public CreateCompanyService(CompanyRepository companyRepository) {
    this.companyRepository = companyRepository;
  }


  public Company execute(CreateCompanyDTO createCompanyDTO) {
    Company company = new Company(createCompanyDTO.getName());
    this.companyRepository.save(company);
    return company;
  }
}
