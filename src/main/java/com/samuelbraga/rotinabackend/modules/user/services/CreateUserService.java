package com.samuelbraga.rotinabackend.modules.user.services;

import com.samuelbraga.rotinabackend.modules.user.dtos.CreateUserDTO;
import com.samuelbraga.rotinabackend.exceptions.BaseException;
import com.samuelbraga.rotinabackend.modules.company.models.Company;
import com.samuelbraga.rotinabackend.modules.user.dtos.UserDTO;
import com.samuelbraga.rotinabackend.modules.user.iservice.ICreateUserService;
import com.samuelbraga.rotinabackend.modules.user.models.User;
import com.samuelbraga.rotinabackend.modules.company.repositories.CompanyRepository;
import com.samuelbraga.rotinabackend.modules.user.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateUserService implements ICreateUserService {
  
  private final UserRepository userRepository;
  private final CompanyRepository companyRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public CreateUserService(UserRepository userRepository, CompanyRepository companyRepository, ModelMapper modelMapper) {
    this.userRepository = userRepository;
    this.companyRepository = companyRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public UserDTO execute(CreateUserDTO createUserDTO) {
    User user = new User(createUserDTO);
    
    Optional<Company> company = this.companyRepository.findById(createUserDTO.getCompanyId());
    
    if(!(company.isPresent())) {
      throw new BaseException("Company does not exists");
    }
    
    user.setCompany(company.get());
    
    this.userRepository.save(user);

    return this.modelMapper.map(user, UserDTO.class);
  }
}