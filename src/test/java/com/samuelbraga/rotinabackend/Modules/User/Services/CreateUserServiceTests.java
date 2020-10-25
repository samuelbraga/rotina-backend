package com.samuelbraga.rotinabackend.Modules.User.Services;

import com.samuelbraga.rotinabackend.Modules.Company.DTOS.CreateCompanyDTO;
import com.samuelbraga.rotinabackend.Modules.Company.Models.Company;
import com.samuelbraga.rotinabackend.Modules.User.DTOS.CreateUserDTO;
import com.samuelbraga.rotinabackend.Modules.User.Models.User;
import com.samuelbraga.rotinabackend.Modules.User.Repositories.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class CreateUserServiceTests {
  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private CreateUserService createUserService;

  @Test
  public void itShouldBeCreatedNewUser() {
    CreateUserDTO createUserDTO = new CreateUserDTO("foo.bar@example.com",
      "Foo",
      "Bar",
      "99999999",
      "12345678",
      "DEFAULT",
      UUID.randomUUID());
    
    User user = this.createUserService.execute(createUserDTO);

    Assert.assertNotNull(user);
    Assert.assertEquals(user.getName(), "Foo");
    Assert.assertEquals(user.getLast_name(), "Bar");
    Assert.assertEquals(user.getEmail(), "foo.bar@example.com");
  }

}
