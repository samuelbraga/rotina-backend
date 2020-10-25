package com.samuelbraga.rotinabackend.Modules.User.Services;

import com.samuelbraga.rotinabackend.Modules.User.DTOS.CreateUserDTO;
import com.samuelbraga.rotinabackend.Exceptions.BaseException;
import com.samuelbraga.rotinabackend.Modules.Company.Models.Company;
import com.samuelbraga.rotinabackend.Modules.User.IService.ICreateUserService;
import com.samuelbraga.rotinabackend.Modules.User.Models.User;
import com.samuelbraga.rotinabackend.Modules.Company.Repositories.CompanyRepository;
import com.samuelbraga.rotinabackend.Modules.User.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateUserService implements ICreateUserService {
  
  private final UserRepository userRepository;
  private final CompanyRepository companyRepository;

  @Autowired
  public CreateUserService(UserRepository userRepository, CompanyRepository companyRepository) {
    this.userRepository = userRepository;
    this.companyRepository = companyRepository;
  }

  @Override
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
