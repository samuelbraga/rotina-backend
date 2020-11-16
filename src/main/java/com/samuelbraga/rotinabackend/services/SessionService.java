package com.samuelbraga.rotinabackend.services;

import com.samuelbraga.rotinabackend.config.security.TokenAuthenticationService;
import com.samuelbraga.rotinabackend.exceptions.BaseException;
import com.samuelbraga.rotinabackend.iservices.ISessionService;
import com.samuelbraga.rotinabackend.dtos.session.CreateSessionDTO;
import com.samuelbraga.rotinabackend.dtos.session.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class SessionService implements ISessionService {
  private final AuthenticationManager authenticationManager;
  private final TokenAuthenticationService tokenAuthenticationService;

  @Autowired
  public SessionService(AuthenticationManager authenticationManager, TokenAuthenticationService tokenAuthenticationService) {
    this.authenticationManager = authenticationManager;
    this.tokenAuthenticationService = tokenAuthenticationService;
  }

  @Override
  public TokenDTO createSession(CreateSessionDTO createSessionDTO) {
    UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(
      createSessionDTO.getEmail(), createSessionDTO.getPassword());

    try {
      Authentication authentication = authenticationManager.authenticate(userAuth);

      String token = tokenAuthenticationService.createToken(authentication);

      return new TokenDTO(token, "Bearer");
    } catch (AuthenticationException error) {
      throw new BaseException("User or password incorrect");
    }
  }
}
