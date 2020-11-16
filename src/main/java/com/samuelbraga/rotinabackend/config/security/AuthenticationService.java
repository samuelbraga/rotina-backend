package com.samuelbraga.rotinabackend.config.security;

import com.samuelbraga.rotinabackend.models.User;
import com.samuelbraga.rotinabackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {
  
  private final UserRepository userRepository;
  
  @Autowired
  public AuthenticationService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) {
    Optional<User> user = this.userRepository.findByEmail(email);
        
    if(!user.isPresent()) {
      throw new UsernameNotFoundException("User does not exist");
    }
    
    return user.get();
  }
}
