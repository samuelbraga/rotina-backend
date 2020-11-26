package com.samuelbraga.rotinabackend.servicesimpl.profile;

import static org.mockito.Mockito.*;

import com.samuelbraga.rotinabackend.dtos.profile.ProfileDTO;
import com.samuelbraga.rotinabackend.enums.TypeUser;
import com.samuelbraga.rotinabackend.exceptions.BaseException;
import com.samuelbraga.rotinabackend.models.Company;
import com.samuelbraga.rotinabackend.models.Profile;
import com.samuelbraga.rotinabackend.models.User;
import com.samuelbraga.rotinabackend.repositories.ProfileRepository;
import com.samuelbraga.rotinabackend.repositories.UserRepository;
import com.samuelbraga.rotinabackend.servicesimpl.ProfileServiceImpl;
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
class ListProfileServiceImplTests {

  @Mock
  private ProfileRepository profileRepository;

  @Mock
  private UserRepository userRepository;

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private ProfileServiceImpl profileService;

  private static final User user;
  private static final Company company;
  private static final UUID userId = UUID.randomUUID();

  static {
    company = Company.builder().id(UUID.randomUUID()).name("foo").build();

    Profile profile = Profile.builder().type(TypeUser.SUPER_ADMIN).build();
    List<Profile> profiles = new ArrayList<>();
    profiles.add(profile);

    user =
      User
        .builder()
        .id(userId)
        .email("foo.bar@example.com")
        .company(company)
        .profiles(profiles)
        .build();
  }

  @BeforeEach
  void init() {
    MockitoAnnotations.openMocks(this);
    this.profileService =
      new ProfileServiceImpl(
        this.profileRepository,
        this.userRepository,
        this.modelMapper
      );
  }

  @Test
  void itShouldBeListProfiles() {
    List<Profile> listProfiles = new ArrayList<>();
    listProfiles.add(new Profile());
    listProfiles.add(new Profile());

    ProfileDTO profileDTO = ProfileDTO.builder().type(TypeUser.DEFAULT).build();

    when(this.userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(this.profileRepository.findAll()).thenReturn(listProfiles);
    when(this.modelMapper.map(any(), any())).thenReturn(profileDTO);

    List<ProfileDTO> result = this.profileService.listProfiles(userId);

    Assert.assertNotNull(result);
    Assert.assertEquals(2, result.size());
  }

  @Test
  void itShouldNotBeListProfilesWithAdminUser() {
    Profile profile = Profile.builder().type(TypeUser.ADMIN).build();
    List<Profile> profiles = new ArrayList<>();
    profiles.add(profile);

    User adminUser = User
      .builder()
      .id(userId)
      .email("foo.bar@example.com")
      .company(company)
      .profiles(profiles)
      .build();

    when(this.userRepository.findById(userId))
      .thenReturn(Optional.of(adminUser));

    Assert.assertThrows(
      BaseException.class,
      () -> this.profileService.listProfiles(userId)
    );
    verify(this.profileRepository, never()).findAll();
  }
}
