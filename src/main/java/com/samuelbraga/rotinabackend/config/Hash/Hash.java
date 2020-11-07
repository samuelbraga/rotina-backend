package com.samuelbraga.rotinabackend.config.Hash;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Hash {

  public String hash(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt(8));
  }

  public boolean verifyHash(String password, String hash) {
    return BCrypt.checkpw(password, hash);
  }
}