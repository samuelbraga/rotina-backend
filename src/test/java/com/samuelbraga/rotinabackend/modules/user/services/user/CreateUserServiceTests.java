package com.samuelbraga.rotinabackend.modules.user.services.user;

import com.samuelbraga.rotinabackend.config.hash.Hash;
import com.samuelbraga.rotinabackend.exceptions.BaseException;
import com.samuelbraga.rotinabackend.modules.company.models.Company;
import com.samuelbraga.rotinabackend.modules.company.repositories.CompanyRepository;
import com.samuelbraga.rotinabackend.modules.user.dtos.user.CreateUserDTO;
import com.samuelbraga.rotinabackend.modules.user.dtos.user.UserDTO;
import com.samuelbraga.rotinabackend.modules.user.enums.TypeUser;
import com.samuelbraga.rotinabackend.modules.user.models.Profile;
import com.samuelbraga.rotinabackend.modules.user.models.User;
import com.samuelbraga.rotinabackend.modules.user.repositories.ProfileRepository;
import com.samuelbraga.rotinabackend.modules.user.repositories.UserRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
      Company company = new Company("foo");

  @InjectMocks
  private CreateUserService createUserService;
  
  @Test
  void itShouldBeCreatedNewUserWithSuperAdmin() {
    UUID companyId = UUID.randomUUID();
    UUID profileId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    
    Company company = new Company("foo");
    
    Profile loggedUserProfile = new Profile();
    loggedUserProfile.setType(TypeUser.SUPER_ADMIN);
    List<Profile> loggedUserProfiles = new ArrayList<>();
    loggedUserProfiles.add(loggedUserProfile);

    User user = new User();
    user.setId(userId);
    user.setEmail("foo.bar@example.com");
    user.setName("Foo");
    user.setLastName("Bar");
    user.setPhone("999999999");
    user.setProfiles(loggedUserProfiles);

    Profile profile = new Profile();
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
    
   
    given(this.companyRepository.findById(companyId)).willReturn(Optional.of(company));
    given(this.profileRepository.findById(profileId)).willReturn(Optional.of(profile));
    when(modelMapper.map(any(), any())).thenReturn(userDTO);
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    UserDTO result = this.createUserService.execute(createUserDTO, userId);

    Assert.assertNotNull(result);
    Assert.assertEquals("Foo", result.getName());
    Assert.assertEquals("Bar", result.getLastName());
    Assert.assertEquals("foo.bar@example.com", result.getEmail());
  }

  @Test
  void itShouldBeCreatedNewUserWithAdmin() {
    UUID companyId = UUID.randomUUID();
    UUID profileId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

    Company company = new Company("foo");
    company.setId(companyId);

    Profile loggedUserProfile = new Profile();
    loggedUserProfile.setType(TypeUser.ADMIN);
    List<Profile> loggedUserProfiles = new ArrayList<>();
    loggedUserProfiles.add(loggedUserProfile);

    User user = new User();
    user.setId(userId);
    user.setEmail("foo.bar@example.com");
    user.setName("Foo");
    user.setLastName("Bar");
    user.setPhone("999999999");
    user.setCompany(company);
    user.setProfiles(loggedUserProfiles);

    Profile profile = new Profile();
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

    given(this.companyRepository.findById(companyId)).willReturn(Optional.of(company));
    given(this.profileRepository.findById(profileId)).willReturn(Optional.of(profile));
    when(modelMapper.map(any(), any())).thenReturn(userDTO);
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    UserDTO result = this.createUserService.execute(createUserDTO, userId);

    Assert.assertNotNull(result);
    Assert.assertEquals("Foo", result.getName());
    Assert.assertEquals("Bar", result.getLastName());
    Assert.assertEquals("foo.bar@example.com", result.getEmail());
  }

  @Test
  void itShouldNotBeCreatedNewUserWithDefault() {
    UUID companyId = UUID.randomUUID();
    UUID profileId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

    Company company = new Company("foo");
    company.setId(companyId);

    Profile loggedUserProfile = new Profile();
    loggedUserProfile.setType(TypeUser.DEFAULT);
    List<Profile> loggedUserProfiles = new ArrayList<>();
    loggedUserProfiles.add(loggedUserProfile);

    User user = new User();
    user.setId(userId);
    user.setEmail("foo.bar@example.com");
    user.setName("Foo");
    user.setLastName("Bar");
    user.setPhone("999999999");
    user.setCompany(company);
    user.setProfiles(loggedUserProfiles);

    Profile profile = new Profile();
    profile.setType(TypeUser.SUPER_ADMIN);

    CreateUserDTO createUserDTO = new CreateUserDTO("foo.bar@example.com",
      "Foo",
      "Bar",
      "99999999",
      "12345678",
      profileId,
      companyId);
    
    given(this.companyRepository.findById(companyId)).willReturn(Optional.of(company));
    given(this.profileRepository.findById(profileId)).willReturn(Optional.of(profile));
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    
    Assert.assertThrows(BaseException.class, () -> this.createUserService.execute(createUserDTO, userId));
    verify(this.companyRepository, never()).save(any(Company.class));
  }
  
  @Test
  void itShouldNotBeCreatedNewUserWithExistsEmail() {
    UUID companyId = UUID.randomUUID();
    UUID profileId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

    Company company = new Company("foo");

    Profile loggedUserProfile = new Profile();
    loggedUserProfile.setType(TypeUser.SUPER_ADMIN);
    List<Profile> loggedUserProfiles = new ArrayList<>();
    loggedUserProfiles.add(loggedUserProfile);

    User user = new User();
    user.setId(userId);
    user.setEmail("foo.bar@example.com");
    user.setName("Foo");
    user.setLastName("Bar");
    user.setPhone("999999999");
    user.setProfiles(loggedUserProfiles);

    Profile profile = new Profile();
    profile.setType(TypeUser.SUPER_ADMIN);

    CreateUserDTO createUserDTO = new CreateUserDTO("foo.bar@example.com",
      "Foo",
      "Bar",
      "99999999",
      "12345678",
      profileId,
      companyId);
    
    given(this.companyRepository.findById(companyId)).willReturn(Optional.of(company));
    given(this.profileRepository.findById(profileId)).willReturn(Optional.of(profile));
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(userRepository.findByEmail(createUserDTO.getEmail())).thenReturn(Optional.of(user));
    
    Assert.assertThrows(BaseException.class, () -> this.createUserService.execute(createUserDTO, userId));
    verify(this.companyRepository, never()).save(any(Company.class));
  }

  @Test
  void itShouldNotBeCreatedNewUserWithCompanyNonExists() {
    UUID companyId = UUID.randomUUID();
    UUID profileId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

    Profile loggedUserProfile = new Profile();
    loggedUserProfile.setType(TypeUser.SUPER_ADMIN);
    List<Profile> loggedUserProfiles = new ArrayList<>();
    loggedUserProfiles.add(loggedUserProfile);

    User user = new User();
    user.setId(userId);
    user.setEmail("foo.bar@example.com");
    user.setName("Foo");
    user.setLastName("Bar");
    user.setPhone("999999999");
    user.setProfiles(loggedUserProfiles);

    Profile profile = new Profile();
    profile.setType(TypeUser.SUPER_ADMIN);

    CreateUserDTO createUserDTO = new CreateUserDTO("foo.bar@example.com",
      "Foo",
      "Bar",
      "99999999",
      "12345678",
      profileId,
      companyId);

    given(this.profileRepository.findById(profileId)).willReturn(Optional.of(profile));
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(userRepository.findByEmail(createUserDTO.getEmail())).thenReturn(Optional.of(user));

    Assert.assertThrows(BaseException.class, () -> this.createUserService.execute(createUserDTO, userId));
    verify(this.companyRepository, never()).save(any(Company.class));
  }

  @Test
  void itShouldNotBeCreatedNewUserWithProfileNonExists() {
    UUID companyId = UUID.randomUUID();
    UUID profileId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

    Profile loggedUserProfile = new Profile();
    loggedUserProfile.setType(TypeUser.SUPER_ADMIN);
    List<Profile> loggedUserProfiles = new ArrayList<>();
    loggedUserProfiles.add(loggedUserProfile);

    User user = new User();
    user.setId(userId);
    user.setEmail("foo.bar@example.com");
    user.setName("Foo");
    user.setLastName("Bar");
    user.setPhone("999999999");
    user.setProfiles(loggedUserProfiles);
    
    CreateUserDTO createUserDTO = new CreateUserDTO("foo.bar@example.com",
      "Foo",
      "Bar",
      "99999999",
      "12345678",
      profileId,
      companyId);

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(userRepository.findByEmail(createUserDTO.getEmail())).thenReturn(Optional.of(user));

    Assert.assertThrows(BaseException.class, () -> this.createUserService.execute(createUserDTO, userId));
    verify(this.companyRepository, never()).save(any(Company.class));
  }

  @Test
  void itShouldNotBeCreatedNewUserWithLoggedUserNonExists() {
    UUID companyId = UUID.randomUUID();
    UUID profileId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    
    User user = new User();
    user.setId(userId);
    user.setEmail("foo.bar@example.com");
    user.setName("Foo");
    user.setLastName("Bar");
    user.setPhone("999999999");

    CreateUserDTO createUserDTO = new CreateUserDTO("foo.bar@example.com",
      "Foo",
      "Bar",
      "99999999",
      "12345678",
      profileId,
      companyId);

    when(userRepository.findByEmail(createUserDTO.getEmail())).thenReturn(Optional.of(user));

    Assert.assertThrows(BaseException.class, () -> this.createUserService.execute(createUserDTO, userId));
    verify(this.companyRepository, never()).save(any(Company.class));
  }
}

