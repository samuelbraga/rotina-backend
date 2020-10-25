package com.samuelbraga.rotinabackend.Modules.Company.Services;

import com.samuelbraga.rotinabackend.Modules.Company.DTOS.CompanyDTO;
import com.samuelbraga.rotinabackend.Modules.Company.DTOS.CreateCompanyDTO;
import com.samuelbraga.rotinabackend.Exceptions.BaseException;
import com.samuelbraga.rotinabackend.Modules.Company.IService.ICreateCompanyService;
import com.samuelbraga.rotinabackend.Modules.Company.Models.Company;
import com.samuelbraga.rotinabackend.Modules.Company.Repositories.CompanyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateCompanyService implements ICreateCompanyService {
  
  private final CompanyRepository companyRepository;
  private final ModelMapper modelMapper;
  
  @Autowired
  public CreateCompanyService(CompanyRepository companyRepository, ModelMapper modelMapper) {
    this.companyRepository = companyRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public CompanyDTO execute(CreateCompanyDTO createCompanyDTO) {
    Optional<Company> companyExists = this.companyRepository.findByName(createCompanyDTO.getName());
    
    if(companyExists.isPresent()) {
      throw new BaseException("Company already exists");
    }
    
    Company company = new Company(createCompanyDTO.getName());
    this.companyRepository.save(company);

    return this.modelMapper.map(company, CompanyDTO.class);
  }
}
