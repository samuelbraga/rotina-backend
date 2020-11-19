package com.samuelbraga.rotinabackend.servicesimpl;

import com.samuelbraga.rotinabackend.dtos.company.CompanyDTO;
import com.samuelbraga.rotinabackend.dtos.company.CreateCompanyDTO;
import com.samuelbraga.rotinabackend.exceptions.BaseException;
import com.samuelbraga.rotinabackend.models.Company;
import com.samuelbraga.rotinabackend.repositories.CompanyRepository;
import com.samuelbraga.rotinabackend.models.User;
import com.samuelbraga.rotinabackend.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CompanyServiceImpl implements com.samuelbraga.rotinabackend.services.CompanyService {
  
  private final CompanyRepository companyRepository;
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  
  @Autowired
  public CompanyServiceImpl(CompanyRepository companyRepository, UserRepository userRepository, ModelMapper modelMapper) {
    this.companyRepository = companyRepository;
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public CompanyDTO createCompany(CreateCompanyDTO createCompanyDTO, UUID userId) {
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
    
    user.orElseThrow(() -> new BaseException("User does not exists"));
    
    return user.get();
  }
}
