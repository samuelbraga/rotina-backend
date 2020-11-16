package com.samuelbraga.rotinabackend.services.user;

import com.samuelbraga.rotinabackend.config.hash.Hash;
import com.samuelbraga.rotinabackend.exceptions.BaseException;
import com.samuelbraga.rotinabackend.models.Company;
import com.samuelbraga.rotinabackend.repositories.CompanyRepository;
import com.samuelbraga.rotinabackend.dtos.user.CreateUserDTO;
import com.samuelbraga.rotinabackend.dtos.user.UserDTO;
import com.samuelbraga.rotinabackend.enums.TypeUser;
import com.samuelbraga.rotinabackend.models.Profile;
import com.samuelbraga.rotinabackend.models.User;
import com.samuelbraga.rotinabackend.repositories.ProfileRepository;
import com.samuelbraga.rotinabackend.repositories.UserRepository;
import com.samuelbraga.rotinabackend.services.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class CreateUserServiceTests {
  @Mock
  private UserRepository userRepository;
  
  @Mock
  private ProfileRepository profileRepository;

  @Mock
  private CompanyRepository companyRepository;

  @Mock
  private ModelMapper modelMapper;

  @Mock
  private Hash hash;

  @InjectMocks
  private UserService userService;
  
  private User user;
  
  private void getLoggedUser() {
    UUID companyId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    
    Company company = new Company("foo");
    company.setId(companyId);
    
    Profile profile = new Profile();
    profile.setType(TypeUser.SUPER_ADMIN);
    List<Profile> profiles = new ArrayList<>();
    profiles.add(profile);

    this.user = new User();
    this.user.setId(userId);
    this.user.setEmail("foo.bar@example.com");
    this.user.setCompany(company);
    this.user.setProfiles(profiles);
  }
  
  @Test
  void itShouldBeCreatedNewUserWithSuperAdmin() {
    UUID companyId = UUID.randomUUID();
    UUID profileId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

    Company company = new Company("foo");
    company.setId(companyId);
    
    this.getLoggedUser();
    this.user.setId(userId);
    
    Profile profile = new Profile();
    profile.setId(profileId);
    profile.setType(TypeUser.SUPER_ADMIN);
    
    CreateUserDTO createUserDTO = new CreateUserDTO("foo.bar@example.com",
      "Foo",
      "Bar",
      "99999999",
      "12345678",
      profileId,
      companyId);

    UserDTO userDTO = new UserDTO();
      
    userDTO.setEmail("foo.bar@example.com");
    userDTO.setName("Foo");
    userDTO.setLastName("Bar");
    userDTO.setPhone("999999999");
    
    when(this.companyRepository.findById(companyId)).thenReturn(Optional.of(company));
    when(this.profileRepository.findById(profileId)).thenReturn(Optional.of(profile));
    when(modelMapper.map(any(), any())).thenReturn(userDTO);
    when(userRepository.findById(userId)).thenReturn(Optional.of(this.user));

    UserDTO result = this.userService.createUser(createUserDTO, userId);

    Assert.assertNotNull(result);
    Assert.assertEquals("Foo", result.getName());
    Assert.assertEquals("Bar", result.getLastName());
    Assert.assertEquals("foo.bar@example.com", result.getEmail());
  }

  @Test
  void itShouldBeCreatedNewUserWithAdmin() {
    UUID userId = UUID.randomUUID();
    UUID profileId = UUID.randomUUID();

    this.getLoggedUser();
    this.user.setId(userId);

    UUID companyId = this.user.getCompany().getId();

    Company company = new Company("foo");
    company.setId(companyId);
    
    Profile profile = new Profile();
    profile.setType(TypeUser.ADMIN);
    List<Profile> profiles = new ArrayList<>();
    profiles.add(profile);

    this.user.setProfiles(profiles);

    CreateUserDTO createUserDTO = new CreateUserDTO("foo.bar@example.com",
      "Foo",
      "Bar",
      "99999999",
      "12345678",
      profileId,
      companyId);

    UserDTO userDTO = new UserDTO();

    userDTO.setEmail("foo.bar@example.com");
    userDTO.setName("Foo");
    userDTO.setLastName("Bar");
    userDTO.setPhone("999999999");

    when(this.companyRepository.findById(companyId)).thenReturn(Optional.of(company));
    when(this.profileRepository.findById(profileId)).thenReturn(Optional.of(profile));
    when(modelMapper.map(any(), any())).thenReturn(userDTO);
    when(userRepository.findById(userId)).thenReturn(Optional.of(this.user));

    UserDTO result = this.userService.createUser(createUserDTO, userId);

    Assert.assertNotNull(result);
    Assert.assertEquals("Foo", result.getName());
    Assert.assertEquals("Bar", result.getLastName());
    Assert.assertEquals("foo.bar@example.com", result.getEmail());
  }

  @Test
  void itShouldNotBeCreatedNewUserWithAdmin() {
    UUID companyId = UUID.randomUUID();
    UUID profileId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

    Company company = new Company("foo");
    company.setId(companyId);

    this.getLoggedUser();
    this.user.setId(userId);

    Profile profile = new Profile();
    profile.setType(TypeUser.ADMIN);
    List<Profile> profiles = new ArrayList<>();
    profiles.add(profile);

    this.user.setProfiles(profiles);

    CreateUserDTO createUserDTO = new CreateUserDTO("bar.foo@example.com",
      "Foo",
      "Bar",
      "99999999",
      "12345678",
      profileId,
      UUID.randomUUID());

    UserDTO userDTO = new UserDTO();

    userDTO.setEmail("foo.bar@example.com");
    userDTO.setName("Foo");
    userDTO.setLastName("Bar");
    userDTO.setPhone("999999999");

    when(this.companyRepository.findById(companyId)).thenReturn(Optional.of(company));
    when(this.profileRepository.findById(profileId)).thenReturn(Optional.of(profile));
    when(modelMapper.map(any(), any())).thenReturn(userDTO);
    when(userRepository.findById(userId)).thenReturn(Optional.of(this.user));

    Assert.assertThrows(BaseException.class, () -> this.userService.createUser(createUserDTO, userId));
    verify(this.companyRepository, never()).save(any(Company.class));
  }

  @Test
  void itShouldNotBeCreatedNewUserWithDefault() {
    UUID companyId = UUID.randomUUID();
    UUID profileId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

    Company company = new Company("foo");
    company.setId(companyId);

    this.getLoggedUser();
    this.user.setId(userId);

    Profile profile = new Profile();
    profile.setType(TypeUser.DEFAULT);
    List<Profile> profiles = new ArrayList<>();
    profiles.add(profile);
    
    this.user.setProfiles(profiles);
    
    CreateUserDTO createUserDTO = new CreateUserDTO("foo.bar@example.com",
      "Foo",
      "Bar",
      "99999999",
      "12345678",
      profileId,
      companyId);

    when(this.companyRepository.findById(companyId)).thenReturn(Optional.of(company));
    when(this.profileRepository.findById(profileId)).thenReturn(Optional.of(profile));
    when(this.userRepository.findById(userId)).thenReturn(Optional.of(this.user));

    Assert.assertThrows(BaseException.class, () -> this.userService.createUser(createUserDTO, userId));
    verify(this.companyRepository, never()).save(any(Company.class));
  }

  @Test
  void itShouldNotBeCreatedNewUserWithExistsEmail() {
    UUID companyId = UUID.randomUUID();
    UUID profileId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

    Company company = new Company("foo");
    company.setId(companyId);

    this.getLoggedUser();
    this.user.setId(userId);

    Profile profile = new Profile();
    profile.setType(TypeUser.SUPER_ADMIN);

    CreateUserDTO createUserDTO = new CreateUserDTO("foo.bar@example.com",
      "Foo",
      "Bar",
      "99999999",
      "12345678",
      profileId,
      companyId);

    when(this.companyRepository.findById(companyId)).thenReturn(Optional.of(company));
    when(this.profileRepository.findById(profileId)).thenReturn(Optional.of(profile));
    when(userRepository.findById(userId)).thenReturn(Optional.of(this.user));
    when(userRepository.findByEmail(createUserDTO.getEmail())).thenReturn(Optional.of(this.user));

    Assert.assertThrows(BaseException.class, () -> this.userService.createUser(createUserDTO, userId));
    verify(this.companyRepository, never()).save(any(Company.class));
  }

  @Test
  void itShouldNotBeCreatedNewUserWithCompanyNonExists() {
    UUID profileId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

    this.getLoggedUser();
    this.user.setId(userId);
    
    Profile profile = new Profile();
    profile.setType(TypeUser.SUPER_ADMIN);

    CreateUserDTO createUserDTO = new CreateUserDTO("foo.bar@example.com",
      "Foo",
      "Bar",
      "99999999",
      "12345678",
      profileId,
      UUID.randomUUID());

    when(this.profileRepository.findById(profileId)).thenReturn(Optional.of(profile));
    when(userRepository.findById(userId)).thenReturn(Optional.of(this.user));

    Assert.assertThrows(BaseException.class, () -> this.userService.createUser(createUserDTO, userId));
    verify(this.companyRepository, never()).save(any(Company.class));
  }

  @Test
  void itShouldNotBeCreatedNewUserWithProfileNonExists() {
    UUID companyId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
   
    Company company = new Company("foo");
    company.setId(companyId);

    this.getLoggedUser();
    this.user.setId(userId);
    
    CreateUserDTO createUserDTO = new CreateUserDTO("foo.bar@example.com",
      "Foo",
      "Bar",
      "99999999",
      "12345678",
      UUID.randomUUID(),
      companyId);

    when(this.companyRepository.findById(companyId)).thenReturn(Optional.of(company));
    when(userRepository.findById(userId)).thenReturn(Optional.of(this.user));

    Assert.assertThrows(BaseException.class, () -> this.userService.createUser(createUserDTO, userId));
    verify(this.companyRepository, never()).save(any(Company.class));
  }

  @Test
  void itShouldNotBeCreatedNewUserWithLoggedUserNonExists() {
    UUID companyId = UUID.randomUUID();
    UUID profileId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

    this.getLoggedUser();
    this.user.setId(userId);
    
    CreateUserDTO createUserDTO = new CreateUserDTO("bar.foo@example.com",
      "Foo",
      "Bar",
      "99999999",
      "12345678",
      profileId,
      companyId);

    Assert.assertThrows(BaseException.class, () -> this.userService.createUser(createUserDTO, userId));
    verify(this.companyRepository, never()).save(any(Company.class));
  }
}

