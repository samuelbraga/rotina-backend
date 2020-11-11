package com.samuelbraga.rotinabackend.config.hash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class Hash {
  
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  
  @Autowired
  public Hash() {
    this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
  }

  public String hash(String password) {
    return bCryptPasswordEncoder.encode(password);
  }
}