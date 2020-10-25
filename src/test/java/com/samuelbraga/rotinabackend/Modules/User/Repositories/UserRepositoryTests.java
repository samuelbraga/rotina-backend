package com.samuelbraga.rotinabackend.Modules.User.Repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class UserRepositoryTests {

  private final UserRepository userRepository;

  @Autowired
  public UserRepositoryTests(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
}
