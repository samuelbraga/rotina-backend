package com.samuelbraga.rotinabackend.modules.user.services.user;

import com.samuelbraga.rotinabackend.config.hash.Hash;
import com.samuelbraga.rotinabackend.modules.user.dtos.user.CreateUserDTO;
import com.samuelbraga.rotinabackend.exceptions.BaseException;
import com.samuelbraga.rotinabackend.modules.company.models.Company;
import com.samuelbraga.rotinabackend.modules.user.dtos.user.UserDTO;
import com.samuelbraga.rotinabackend.modules.user.iservice.user.ICreateUserService;
import com.samuelbraga.rotinabackend.modules.user.models.User;
import com.samuelbraga.rotinabackend.modules.company.repositories.CompanyRepository;
import com.samuelbraga.rotinabackend.modules.user.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CreateUserService implements ICreateUserService {
  
  private final UserRepository userRepository;
  private final CompanyRepository companyRepository;
  private final Hash hash;
  private final ModelMapper modelMapper;
  

  @Autowired
  public CreateUserService(UserRepository userRepository, CompanyRepository companyRepository, Hash hash, ModelMapper modelMapper) {
    this.userRepository = userRepository;
    this.companyRepository = companyRepository;
    this.modelMapper = modelMapper;
    this.hash = hash;
  }

  @Override
  public UserDTO execute(CreateUserDTO createUserDTO) {
    this.verifyUserEmailExist(createUserDTO.getEmail());
    Company company = this.findCompany(createUserDTO.getCompanyId());    
    
    String hashedPassword = hashPassword(createUserDTO.getPassword());
    createUserDTO.setPassword(hashedPassword);
    
    User user = new User(createUserDTO);
    user.setCompany(company);
    this.userRepository.save(user);

    return this.modelMapper.map(user, UserDTO.class);
  }
  
  private String hashPassword(String password) {
    return this.hash.hash(password);
  }
  
  private void verifyUserEmailExist(String email) {
    Optional<User> user = this.userRepository.findByEmail(email);
    
    if(user.isPresent()) {
      throw new BaseException("User email already exists");
    }
  }
  
  private Company findCompany(UUID companyId){
    Optional<Company> company = this.companyRepository.findById(companyId);
    
    if(!(company.isPresent())) {
      throw new BaseException("Company does not exists");
    }
    
    return company.get();
  }
}
