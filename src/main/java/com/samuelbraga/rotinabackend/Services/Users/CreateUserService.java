package com.samuelbraga.rotinabackend.Services.Users;

import com.samuelbraga.rotinabackend.DTO.Users.CreateUserDTO;
import com.samuelbraga.rotinabackend.Exception.BaseException;
import com.samuelbraga.rotinabackend.Models.Company;
import com.samuelbraga.rotinabackend.Models.User;
import com.samuelbraga.rotinabackend.Repository.CompanyRepository;
import com.samuelbraga.rotinabackend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateUserService {
  
  private final UserRepository userRepository;
  private final CompanyRepository companyRepository;

  @Autowired
  public CreateUserService(UserRepository userRepository, CompanyRepository companyRepository) {
    this.userRepository = userRepository;
    this.companyRepository = companyRepository;
  }
  
  
  public User execute(CreateUserDTO createUserDTO) {
    User user = new User(createUserDTO);
    
    Optional<Company> company = this.companyRepository.findById(createUserDTO.getCompany_id());
    
    if(!(company.isPresent())) {
      throw new BaseException("Company does not exists");
    }
    
    user.setCompany(company.get());
    
    this.userRepository.save(user);
    return user;
  }
}
