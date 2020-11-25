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
import java.util.Optional;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class CreateSessionServiceImplTest {

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private TokenAuthenticationService tokenAuthenticationService;

  @InjectMocks
  private SessionServiceImpl sessionServiceImpl;

  private static List<Profile> profiles = new ArrayList<>();
  
  private static UsernamePasswordAuthenticationToken userAuth;
  
  static {
    Profile profile = new Profile();
    profile.setType(TypeUser.SUPER_ADMIN);
    profiles.add(profile);

    userAuth = new UsernamePasswordAuthenticationToken(
      "foo.bar@example.com",
      "123456"
    );
  }  
    
  @Before
  void init() {
    MockitoAnnotations.openMocks(this);
    sessionServiceImpl = new SessionServiceImpl(this.authenticationManager, this.tokenAuthenticationService);
  }

  @Test
  void itShouldBeCreatedNewUserSession() {
    when(this.authenticationManager.authenticate(userAuth)).thenReturn(userAuth);
    
    CreateSessionDTO createSessionDTO = new CreateSessionDTO(
      "foo.bar@example.com",
      "123456"
    );

    TokenDTO result = this.sessionServiceImpl.createSession(createSessionDTO);

    Assert.assertNotNull(result);
  }

  @Test
  void itShouldNotBeCreatedNewUserSession() {
    doThrow(BadCredentialsException.class).when(this.authenticationManager).authenticate(userAuth);
    
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
