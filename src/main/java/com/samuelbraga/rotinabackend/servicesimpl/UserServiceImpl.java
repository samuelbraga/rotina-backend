package com.samuelbraga.rotinabackend.servicesimpl;

import com.samuelbraga.rotinabackend.config.hash.Hash;
import com.samuelbraga.rotinabackend.exceptions.BaseException;
import com.samuelbraga.rotinabackend.models.Company;
import com.samuelbraga.rotinabackend.repositories.CompanyRepository;
import com.samuelbraga.rotinabackend.dtos.user.CreateUserDTO;
import com.samuelbraga.rotinabackend.dtos.user.UserDTO;
import com.samuelbraga.rotinabackend.models.Profile;
import com.samuelbraga.rotinabackend.models.User;
import com.samuelbraga.rotinabackend.repositories.ProfileRepository;
import com.samuelbraga.rotinabackend.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements com.samuelbraga.rotinabackend.services.UserService {
  private final UserRepository userRepository;
  private final ProfileRepository profileRepository;
  private final CompanyRepository companyRepository;
  private final Hash hash;
  private final ModelMapper modelMapper;


  @Autowired
  public UserServiceImpl(UserRepository userRepository, ProfileRepository profileRepository, CompanyRepository companyRepository, Hash hash, ModelMapper modelMapper) {
    this.userRepository = userRepository;
    this.profileRepository = profileRepository;
    this.companyRepository = companyRepository;
    this.modelMapper = modelMapper;
    this.hash = hash;
  }

  @Override
  public UserDTO createUser(CreateUserDTO createUserDTO, UUID userId) {
    this.verifyUserIsAuthorized(userId, createUserDTO.getCompanyId());
    this.verifyUserEmailExist(createUserDTO.getEmail());

    Company company = this.findCompany(createUserDTO.getCompanyId());
    List<Profile> profiles = this.findProfiles(createUserDTO.getProfileId());

    String hashedPassword = hashPassword(createUserDTO.getPassword());
    createUserDTO.setPassword(hashedPassword);

    User user = new User(createUserDTO);
    user.setCompany(company);
    user.setProfiles(profiles);

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

    company.orElseThrow(() -> new BaseException("Company does not exists"));
   
    return company.get();
  }

  private List<Profile> findProfiles(UUID profileId) {
    Optional<Profile> profile = this.profileRepository.findById(profileId);

    profile.orElseThrow(() -> new BaseException("Profile does not exists"));

    ArrayList<Profile> profiles = new ArrayList<>();
    profiles.add(profile.get());
    
    return profiles;
  }

  private User getUser(UUID userId) {
    Optional<User> user = this.userRepository.findById(userId);

    user.orElseThrow(() -> new BaseException("User does not exists"));

    return user.get();
  }

  private void verifyUserIsAuthorized(UUID userId, UUID companyId) {
    User user = this.getUser(userId);

    if(user.isSuperAdmin()) {
      return;
    }

    if(user.isAdmin() && user.getCompany().getId() == companyId) {
      return;
    }

    throw new BaseException("User does not permission");
  }
}
