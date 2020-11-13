package com.samuelbraga.rotinabackend.modules.user.services.session;

import com.samuelbraga.rotinabackend.config.security.TokenAuthenticationService;
import com.samuelbraga.rotinabackend.exceptions.BaseException;
import com.samuelbraga.rotinabackend.modules.user.dtos.session.CreateSessionDTO;
import com.samuelbraga.rotinabackend.modules.user.dtos.session.TokenDTO;
import com.samuelbraga.rotinabackend.modules.user.enums.TypeUser;
import com.samuelbraga.rotinabackend.modules.user.models.Profile;
import com.samuelbraga.rotinabackend.modules.user.models.User;
import com.samuelbraga.rotinabackend.modules.user.repositories.ProfileRepository;
import com.samuelbraga.rotinabackend.modules.user.repositories.UserRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class CreateSessionServiceTest {
  @Mock
  private final AuthenticationManager authenticationManager;

  @Mock
  private final TokenAuthenticationService tokenAuthenticationService;

  @InjectMocks
  private final CreateSessionService createSessionService;

  @InjectMocks
  private final ProfileRepository profileRepository;
  
  @InjectMocks
  private final UserRepository userRepository;
  
  @Autowired
  public CreateSessionServiceTest(AuthenticationManager authenticationManager, TokenAuthenticationService tokenAuthenticationService, CreateSessionService createSessionService, ProfileRepository profileRepository, UserRepository userRepository) {
    this.authenticationManager = authenticationManager;
    this.tokenAuthenticationService = tokenAuthenticationService;
    this.createSessionService = createSessionService;
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
    
    CreateSessionDTO createSessionDTO = new CreateSessionDTO("foo.bar@example.com", "123456");
    
    TokenDTO result = this.createSessionService.execute(createSessionDTO);

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

    CreateSessionDTO createSessionDTO = new CreateSessionDTO("bar.foo@example.com", "12345678");
    
    Assert.assertThrows(BaseException.class, () -> this.createSessionService.execute(createSessionDTO));
  }
}

