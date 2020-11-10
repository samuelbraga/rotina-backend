package com.samuelbraga.rotinabackend.config.security;

import com.samuelbraga.rotinabackend.exceptions.BaseException;
import com.samuelbraga.rotinabackend.modules.user.models.User;
import com.samuelbraga.rotinabackend.modules.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class AuthenticationFilter extends OncePerRequestFilter {
  private final TokenAuthenticationService tokenAuthenticationService;
  private final UserRepository userRepository;

  public AuthenticationFilter(TokenAuthenticationService tokenAuthenticationService, UserRepository userRepository) {
    this.tokenAuthenticationService = tokenAuthenticationService;
    this.userRepository = userRepository;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
    String token = recoveryToken(httpServletRequest);
    boolean isValidToken = tokenAuthenticationService.isTokenValid(token);
    
    if(isValidToken) {
      authenticatedClient(token);
    }
    
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }
  
  private String recoveryToken(HttpServletRequest request) {
    String token = request.getHeader("Authorization");
    
    if( token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
      return null;
    }
    
    return token.substring(7);
  }
  
  private void authenticatedClient(String token) {
    UUID userId = tokenAuthenticationService.getUserId(token);
    Optional<User> optionalUser = this.userRepository.findById(userId);
    
    if(!optionalUser.isPresent()) {
      throw new BaseException("User does not exists");
    }
    
    User user = optionalUser.get();
    
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}
