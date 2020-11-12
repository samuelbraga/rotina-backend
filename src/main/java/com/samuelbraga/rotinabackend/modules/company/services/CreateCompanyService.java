package com.samuelbraga.rotinabackend.modules.company.services;

import com.samuelbraga.rotinabackend.modules.company.dtos.CompanyDTO;
import com.samuelbraga.rotinabackend.modules.company.dtos.CreateCompanyDTO;
import com.samuelbraga.rotinabackend.exceptions.BaseException;
import com.samuelbraga.rotinabackend.modules.company.iservice.ICreateCompanyService;
import com.samuelbraga.rotinabackend.modules.company.models.Company;
import com.samuelbraga.rotinabackend.modules.company.repositories.CompanyRepository;
import com.samuelbraga.rotinabackend.modules.user.models.User;
import com.samuelbraga.rotinabackend.modules.user.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CreateCompanyService implements ICreateCompanyService {
  
  private final CompanyRepository companyRepository;
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  
  @Autowired
  public CreateCompanyService(CompanyRepository companyRepository, UserRepository userRepository, ModelMapper modelMapper) {
    this.companyRepository = companyRepository;
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public CompanyDTO execute(CreateCompanyDTO createCompanyDTO, UUID userId) {
    User user = this.getUser(userId);
    
    if(!user.isSuperAdmin()) {
      throw new BaseException("User does not permission");
    }
        
    Optional<Company> companyExists = this.companyRepository.findByName(createCompanyDTO.getName());
    
    if(companyExists.isPresent()) {
      throw new BaseException("Company already exists");
    }
    
    Company company = new Company(createCompanyDTO.getName());
    this.companyRepository.save(company);

    return this.modelMapper.map(company, CompanyDTO.class);
  }
  
  private User getUser(UUID userId) {
    Optional<User> user = this.userRepository.findById(userId);
    
    if(!user.isPresent()) {
      throw new BaseException("User does not exists");
    }
    
    return user.get();
  }
}
