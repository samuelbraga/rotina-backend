package com.samuelbraga.rotinabackend.Modules.User.Services;

import com.samuelbraga.rotinabackend.Modules.User.DTOS.CreateUserDTO;
import com.samuelbraga.rotinabackend.Exceptions.BaseException;
import com.samuelbraga.rotinabackend.Modules.Company.Models.Company;
import com.samuelbraga.rotinabackend.Modules.User.DTOS.UserDTO;
import com.samuelbraga.rotinabackend.Modules.User.IService.ICreateUserService;
import com.samuelbraga.rotinabackend.Modules.User.Models.User;
import com.samuelbraga.rotinabackend.Modules.Company.Repositories.CompanyRepository;
import com.samuelbraga.rotinabackend.Modules.User.Repositories.UserRepository;
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
    
    Optional<Company> company = this.companyRepository.findById(createUserDTO.getCompany_id());
    
    if(!(company.isPresent())) {
      throw new BaseException("Company does not exists");
    }
    
    user.setCompany(company.get());
    
    this.userRepository.save(user);

    return this.modelMapper.map(user, UserDTO.class);
  }
}
