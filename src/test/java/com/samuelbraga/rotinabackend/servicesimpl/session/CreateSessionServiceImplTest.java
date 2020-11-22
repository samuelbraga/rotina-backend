package com.samuelbraga.rotinabackend.servicesimpl.session;

import com.samuelbraga.rotinabackend.config.security.TokenAuthenticationService;
import com.samuelbraga.rotinabackend.dtos.session.CreateSessionDTO;
import com.samuelbraga.rotinabackend.dtos.session.TokenDTO;
import com.samuelbraga.rotinabackend.enums.TypeUser;
import com.samuelbraga.rotinabackend.exceptions.BaseException;
import com.samuelbraga.rotinabackend.models.Profile;
import com.samuelbraga.rotinabackend.models.User;
import com.samuelbraga.rotinabackend.repositories.ProfileRepository;
import com.samuelbraga.rotinabackend.repositories.UserRepository;
import com.samuelbraga.rotinabackend.servicesimpl.SessionServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class CreateSessionServiceImplTest {

  @Mock
  private final AuthenticationManager authenticationManager;

  @Mock
  private final TokenAuthenticationService tokenAuthenticationService;

  @InjectMocks
  private final SessionServiceImpl sessionServiceImpl;

  @InjectMocks
  private final ProfileRepository profileRepository;

  @InjectMocks
  private final UserRepository userRepository;

  @Autowired
  public CreateSessionServiceImplTest(
    AuthenticationManager authenticationManager,
    TokenAuthenticationService tokenAuthenticationService,
    SessionServiceImpl sessionServiceImpl,
    ProfileRepository profileRepository,
    UserRepository userRepository
  ) {
    this.authenticationManager = authenticationManager;
    this.tokenAuthenticationService = tokenAuthenticationService;
    this.sessionServiceImpl = sessionServiceImpl;
    this.profileRepository = profileRepository;
    this.userRepository = userRepository;
  }

  @Test
  void itShouldBeCreatedNewUserSession() {
    Profile profile = new Profile();
    profile.setType(TypeUser.SUPER_ADMIN);
    this.profileRepository.save(profile);

    List<Profile> profiles = new ArrayList<>();
    profiles.add(profile);

    User user = new User();
    user.setId(UUID.randomUUID());
    user.setEmail("foo.bar@example.com");
    user.setPassword(new BCryptPasswordEncoder().encode("123456"));
    user.setProfiles(profiles);
    this.userRepository.save(user);

    CreateSessionDTO createSessionDTO = new CreateSessionDTO(
      "foo.bar@example.com",
      "123456"
    );

    TokenDTO result = this.sessionServiceImpl.createSession(createSessionDTO);

    Assert.assertNotNull(result);
  }

  @Test
  void itShouldNotBeCreatedNewUserSession() {
    Profile profile = new Profile();
    profile.setType(TypeUser.SUPER_ADMIN);
    this.profileRepository.save(profile);

    List<Profile> profiles = new ArrayList<>();
    profiles.add(profile);

    User user = new User();
    user.setId(UUID.randomUUID());
    user.setEmail("bar.foo@example.com");
    user.setPassword(new BCryptPasswordEncoder().encode("123456"));
    user.setProfiles(profiles);
    this.userRepository.save(user);

    CreateSessionDTO createSessionDTO = new CreateSessionDTO(
      "bar.foo@example.com",
      "12345678"
    );

    Assert.assertThrows(
      BaseException.class,
      () -> this.sessionServiceImpl.createSession(createSessionDTO)
    );
  }
}
