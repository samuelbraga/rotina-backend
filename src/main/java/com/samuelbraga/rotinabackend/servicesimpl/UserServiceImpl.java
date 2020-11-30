package com.samuelbraga.rotinabackend.servicesimpl;

import com.samuelbraga.rotinabackend.config.hash.Hash;
import com.samuelbraga.rotinabackend.dtos.user.CreateUserDTO;
import com.samuelbraga.rotinabackend.dtos.user.UserDTO;
import com.samuelbraga.rotinabackend.exceptions.BaseException;
import com.samuelbraga.rotinabackend.models.Company;
import com.samuelbraga.rotinabackend.models.Profile;
import com.samuelbraga.rotinabackend.models.User;
import com.samuelbraga.rotinabackend.repositories.CompanyRepository;
import com.samuelbraga.rotinabackend.repositories.ProfileRepository;
import com.samuelbraga.rotinabackend.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl
  implements com.samuelbraga.rotinabackend.services.UserService {

  private final UserRepository userRepository;
  private final ProfileRepository profileRepository;
  private final CompanyRepository companyRepository;
  private final Hash hash;
  private final ModelMapper modelMapper;

  @Autowired
  public UserServiceImpl(
    UserRepository userRepository,
    ProfileRepository profileRepository,
    CompanyRepository companyRepository,
    Hash hash,
    ModelMapper modelMapper
  ) {
    this.userRepository = userRepository;
    this.profileRepository = profileRepository;
    this.companyRepository = companyRepository;
    this.modelMapper = modelMapper;
    this.hash = hash;
  }

  @Override
  public UserDTO createUser(CreateUserDTO createUserDTO, UUID userId) {
    User user = this.getUser(userId);

    if (user.isCompanyAuthorized(createUserDTO.getCompanyId())) {
      throw new BaseException("User does not permission");
    }

    this.verifyUserEmailExist(createUserDTO.getEmail());

    Company company = this.findCompany(createUserDTO.getCompanyId());
    List<Profile> profiles = this.findProfiles(createUserDTO.getProfileId());

    String hashedPassword = hashPassword(createUserDTO.getPassword());
    createUserDTO.setPassword(hashedPassword);

    User newUser = new User(createUserDTO);
    user.setCompany(company);
    user.setProfiles(profiles);

    this.userRepository.save(newUser);

    return this.modelMapper.map(newUser, UserDTO.class);
  }
  
  @Override
  public List<UserDTO> listUsers(UUID userId) {
    if (this.getUser(userId).isSuperAdmin()) {
      throw new BaseException("User does not permission");
    }
    
    List<User> users = this.userRepository.findAll();
    
    return users.stream().map(user -> this.modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
  }

  private String hashPassword(String password) {
    return this.hash.hash(password);
  }

  private void verifyUserEmailExist(String email) {
    Optional<User> user = this.userRepository.findByEmail(email);

    if (user.isPresent()) {
      throw new BaseException("User email already exists");
    }
  }

  private Company findCompany(UUID companyId) {
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
}
