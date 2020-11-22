package com.samuelbraga.rotinabackend.config.security;

import com.samuelbraga.rotinabackend.exceptions.BaseException;
import com.samuelbraga.rotinabackend.models.User;
import com.samuelbraga.rotinabackend.repositories.UserRepository;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthenticationFilter extends OncePerRequestFilter {

  private final TokenAuthenticationService tokenAuthenticationService;
  private final UserRepository userRepository;

  public AuthenticationFilter(
    TokenAuthenticationService tokenAuthenticationService,
    UserRepository userRepository
  ) {
    this.tokenAuthenticationService = tokenAuthenticationService;
    this.userRepository = userRepository;
  }

  @Override
  protected void doFilterInternal(
    HttpServletRequest httpServletRequest,
    HttpServletResponse httpServletResponse,
    FilterChain filterChain
  ) throws ServletException, IOException {
    String token = recoveryToken(httpServletRequest);
    boolean isValidToken = tokenAuthenticationService.isTokenValid(token);

    if (isValidToken) {
      UUID userId = tokenAuthenticationService.getUserId(token);

      authenticatedClient(userId);

      HttpSession session = httpServletRequest.getSession(true);
      session.setAttribute("userId", userId);
    }

    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }

  private String recoveryToken(HttpServletRequest request) {
    String token = request.getHeader("Authorization");

    if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
      return null;
    }

    return token.substring(7);
  }

  private void authenticatedClient(UUID userId) {
    Optional<User> optionalUser = this.userRepository.findById(userId);

    optionalUser.orElseThrow(() -> new BaseException("User does not exists"));

    User user = optionalUser.get();

    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
      user,
      null,
      user.getAuthorities()
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}
