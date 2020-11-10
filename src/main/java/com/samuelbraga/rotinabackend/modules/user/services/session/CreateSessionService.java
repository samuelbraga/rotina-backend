package com.samuelbraga.rotinabackend.modules.user.services.session;

import com.samuelbraga.rotinabackend.config.security.TokenAuthenticationService;
import com.samuelbraga.rotinabackend.exceptions.BaseException;
import com.samuelbraga.rotinabackend.modules.user.dtos.session.CreateSessionDTO;
import com.samuelbraga.rotinabackend.modules.user.dtos.session.TokenDTO;
import com.samuelbraga.rotinabackend.modules.user.iservice.session.ICreateSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class CreateSessionService implements ICreateSessionService {
  
  private final AuthenticationManager authenticationManager;
  private final TokenAuthenticationService tokenAuthenticationService;
  
  private String  AUTH_TYPE = "Bearer";
  
  @Autowired
  public CreateSessionService(AuthenticationManager authenticationManager, TokenAuthenticationService tokenAuthenticationService) {
    this.authenticationManager = authenticationManager;
    this.tokenAuthenticationService = tokenAuthenticationService;
  }

  @Override
  public TokenDTO execute(CreateSessionDTO createSessionDTO) {
    UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(
      createSessionDTO.getEmail(), createSessionDTO.getPassword());
        
    try {
      Authentication authentication = authenticationManager.authenticate(userAuth);

      String token = tokenAuthenticationService.createToken(authentication);
      TokenDTO tokenDTO = new TokenDTO(token, AUTH_TYPE);

      return tokenDTO;
    } catch (AuthenticationException error) {
      throw new BaseException("User or password incorrect");
    }
  }
}
