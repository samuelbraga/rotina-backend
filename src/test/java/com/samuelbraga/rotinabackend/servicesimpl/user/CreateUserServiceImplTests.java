package com.samuelbraga.rotinabackend.servicesimpl.user;

import static org.mockito.Mockito.*;

import com.samuelbraga.rotinabackend.config.hash.Hash;
import com.samuelbraga.rotinabackend.dtos.user.CreateUserDTO;
import com.samuelbraga.rotinabackend.dtos.user.UserDTO;
import com.samuelbraga.rotinabackend.enums.TypeUser;
import com.samuelbraga.rotinabackend.exceptions.BaseException;
import com.samuelbraga.rotinabackend.models.Company;
import com.samuelbraga.rotinabackend.models.Profile;
import com.samuelbraga.rotinabackend.models.User;
import com.samuelbraga.rotinabackend.repositories.CompanyRepository;
import com.samuelbraga.rotinabackend.repositories.ProfileRepository;
import com.samuelbraga.rotinabackend.repositories.UserRepository;
import com.samuelbraga.rotinabackend.servicesimpl.UserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class CreateUserServiceImplTests {

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
  private UserServiceImpl userService;

  private static final User user;
  private static final Company company;
  private static final Profile profile;
  private static final List<Profile> profiles;
  private static final CreateUserDTO createUserDTO;
  private static final UserDTO userDTO;
  private static final UUID userId = UUID.randomUUID();
  private static final UUID companyId = UUID.randomUUID();
  private static final UUID profileId = UUID.randomUUID();

  static {
    company = Company.builder().id(companyId).name("foo").build();

    profile =
      Profile.builder().id(profileId).type(TypeUser.SUPER_ADMIN).build();
    profiles = new ArrayList<>();
    profiles.add(profile);

    user =
      User
        .builder()
        .id(userId)
        .email("foo.bar@example.com")
        .company(company)
        .profiles(profiles)
        .build();

    createUserDTO =
      CreateUserDTO
        .builder()
        .email("foo.bar@example.com")
        .name("Foo")
        .lastName("Bar")
        .phone("99999999")
        .password("12345678")
        .profileId(profileId)
        .companyId(companyId)
        .build();

    userDTO =
      UserDTO
        .builder()
        .email("foo.bar@example.com")
        .name("Foo")
        .lastName("Bar")
        .phone("999999999")
        .build();
  }

  @BeforeEach
  void init() {
    MockitoAnnotations.openMocks(this);
    this.userService =
      new UserServiceImpl(
        this.userRepository,
        this.profileRepository,
        this.companyRepository,
        this.hash,
        this.modelMapper
      );
  }

  @Test
  void itShouldBeCreatedNewUserWithSuperAdmin() {
    when(this.companyRepository.findById(companyId))
      .thenReturn(Optional.of(company));
    when(this.profileRepository.findById(profileId))
      .thenReturn(Optional.of(profile));
    when(this.modelMapper.map(any(), any())).thenReturn(userDTO);
    when(this.userRepository.findById(userId)).thenReturn(Optional.of(user));

    UserDTO result = this.userService.createUser(createUserDTO, userId);

    Assert.assertNotNull(result);
    Assert.assertEquals("Foo", result.getName());
    Assert.assertEquals("Bar", result.getLastName());
    Assert.assertEquals("foo.bar@example.com", result.getEmail());
  }

  @Test
  void itShouldBeCreatedNewUserWithAdmin() {
    Profile profileAdmin = Profile
      .builder()
      .id(profileId)
      .type(TypeUser.ADMIN)
      .build();
    List<Profile> profilesAdmin = new ArrayList<>();
    profilesAdmin.add(profileAdmin);

    User userAdmin = User
      .builder()
      .id(userId)
      .email("foo.bar@example.com")
      .company(company)
      .profiles(profilesAdmin)
      .build();

    when(this.companyRepository.findById(companyId))
      .thenReturn(Optional.of(company));
    when(this.profileRepository.findById(profileId))
      .thenReturn(Optional.of(profileAdmin));
    when(this.modelMapper.map(any(), any())).thenReturn(userDTO);
    when(this.userRepository.findById(userId))
      .thenReturn(Optional.of(userAdmin));

    UserDTO result = this.userService.createUser(createUserDTO, userId);

    Assert.assertNotNull(result);
    Assert.assertEquals("Foo", result.getName());
    Assert.assertEquals("Bar", result.getLastName());
    Assert.assertEquals("foo.bar@example.com", result.getEmail());
  }

  @Test
  void itShouldNotBeCreatedNewUserWithAdmin() {
    Company companyTest = Company
      .builder()
      .id(UUID.randomUUID())
      .name("foo")
      .build();

    Profile profileAdmin = Profile
      .builder()
      .id(profileId)
      .type(TypeUser.ADMIN)
      .build();
    List<Profile> profilesAdmin = new ArrayList<>();
    profilesAdmin.add(profileAdmin);

    User userAdmin = User
      .builder()
      .id(userId)
      .email("foo.bar@example.com")
      .company(companyTest)
      .profiles(profilesAdmin)
      .build();

    when(this.companyRepository.findById(companyId))
      .thenReturn(Optional.of(company));
    when(this.profileRepository.findById(profileId))
      .thenReturn(Optional.of(profileAdmin));
    when(this.userRepository.findById(userId))
      .thenReturn(Optional.of(userAdmin));

    Assert.assertThrows(
      BaseException.class,
      () -> this.userService.createUser(createUserDTO, userId)
    );
    verify(this.companyRepository, never()).save(any(Company.class));
  }

  @Test
  void itShouldNotBeCreatedNewUserWithDefault() {
    Profile profileDefault = Profile
      .builder()
      .id(profileId)
      .type(TypeUser.DEFAULT)
      .build();
    List<Profile> profilesDefault = new ArrayList<>();
    profilesDefault.add(profileDefault);

    User userDefault = User
      .builder()
      .id(userId)
      .email("foo.bar@example.com")
      .company(company)
      .profiles(profilesDefault)
      .build();

    when(this.companyRepository.findById(companyId))
      .thenReturn(Optional.of(company));
    when(this.profileRepository.findById(profileId))
      .thenReturn(Optional.of(profile));
    when(this.userRepository.findById(userId))
      .thenReturn(Optional.of(userDefault));

    Assert.assertThrows(
      BaseException.class,
      () -> this.userService.createUser(createUserDTO, userId)
    );
    verify(this.companyRepository, never()).save(any(Company.class));
  }

  @Test
  void itShouldNotBeCreatedNewUserWithExistsEmail() {
    when(this.companyRepository.findById(companyId))
      .thenReturn(Optional.of(company));
    when(this.profileRepository.findById(profileId))
      .thenReturn(Optional.of(profile));
    when(this.userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(this.userRepository.findByEmail(createUserDTO.getEmail()))
      .thenReturn(Optional.of(user));

    Assert.assertThrows(
      BaseException.class,
      () -> this.userService.createUser(createUserDTO, userId)
    );
    verify(this.companyRepository, never()).save(any(Company.class));
  }

  @Test
  void itShouldNotBeCreatedNewUserWithCompanyNonExists() {
    when(this.profileRepository.findById(profileId))
      .thenReturn(Optional.of(profile));
    when(this.userRepository.findById(userId)).thenReturn(Optional.of(user));

    Assert.assertThrows(
      BaseException.class,
      () -> this.userService.createUser(createUserDTO, userId)
    );
    verify(this.companyRepository, never()).save(any(Company.class));
  }

  @Test
  void itShouldNotBeCreatedNewUserWithProfileNonExists() {
    when(this.companyRepository.findById(companyId))
      .thenReturn(Optional.of(company));
    when(this.userRepository.findById(userId)).thenReturn(Optional.of(user));

    Assert.assertThrows(
      BaseException.class,
      () -> this.userService.createUser(createUserDTO, userId)
    );
    verify(this.companyRepository, never()).save(any(Company.class));
  }

  @Test
  void itShouldNotBeCreatedNewUserWithLoggedUserNonExists() {
    Assert.assertThrows(
      BaseException.class,
      () -> this.userService.createUser(createUserDTO, userId)
    );
    verify(this.companyRepository, never()).save(any(Company.class));
  }
}
