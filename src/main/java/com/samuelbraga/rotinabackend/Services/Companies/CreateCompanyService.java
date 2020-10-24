package com.samuelbraga.rotinabackend.Services.Companies;

import com.samuelbraga.rotinabackend.DTO.Companies.CreateCompanyDTO;
import com.samuelbraga.rotinabackend.Exception.BaseException;
import com.samuelbraga.rotinabackend.Models.Company;
import com.samuelbraga.rotinabackend.Repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateCompanyService {
  
  private final CompanyRepository companyRepository;
  
  @Autowired
  public CreateCompanyService(CompanyRepository companyRepository) {
    this.companyRepository = companyRepository;
  }


  public Company execute(CreateCompanyDTO createCompanyDTO) {
    Optional<Company> companyExists = this.companyRepository.findByName(createCompanyDTO.getName());
    
    if(companyExists.isPresent()) {
      throw new BaseException("Company already exists");
    }
    
    Company company = new Company(createCompanyDTO.getName());
    this.companyRepository.save(company);
    return company;
  }
}
