package com.samuelbraga.rotinabackend.servicesimpl.session;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.samuelbraga.rotinabackend.config.security.TokenAuthenticationService;
import com.samuelbraga.rotinabackend.dtos.session.CreateSessionDTO;
import com.samuelbraga.rotinabackend.dtos.session.TokenDTO;
import com.samuelbraga.rotinabackend.exceptions.BaseException;
import com.samuelbraga.rotinabackend.servicesimpl.SessionServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;

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

  private static final UsernamePasswordAuthenticationToken userAuth;

  static {
    userAuth =
      new UsernamePasswordAuthenticationToken("foo.bar@example.com", "123456");
  }

  @BeforeEach
  void init() {
    MockitoAnnotations.openMocks(this);
    this.sessionServiceImpl =
      new SessionServiceImpl(
        this.authenticationManager,
        this.tokenAuthenticationService
      );
  }

  @Test
  void itShouldBeCreatedNewUserSession() {
    when(this.authenticationManager.authenticate(userAuth))
      .thenReturn(userAuth);

    CreateSessionDTO createSessionDTO = new CreateSessionDTO(
      "foo.bar@example.com",
      "123456"
    );

    TokenDTO result = this.sessionServiceImpl.createSession(createSessionDTO);

    Assert.assertNotNull(result);
  }

  @Test
  void itShouldNotBeCreatedNewUserSession() {
    when(this.authenticationManager.authenticate(any()))
      .thenThrow(new BadCredentialsException("Bad credentials"));

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
