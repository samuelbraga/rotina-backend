package com.samuelbraga.rotinabackend.Modules.User.Services;

import com.samuelbraga.rotinabackend.Modules.Company.Models.Company;
import com.samuelbraga.rotinabackend.Modules.Company.Repositories.CompanyRepository;
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

import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class CreateUserServiceTests {
  @Mock
  private UserRepository userRepository;

  @Mock
  private CompanyRepository companyRepository;

  @InjectMocks
  private CreateUserService createUserService;

  @Test
  public void itShouldBeCreatedNewUser() {
    Company company = new Company("foo");
    
    UUID uuid = UUID.randomUUID();
    CreateUserDTO createUserDTO = new CreateUserDTO("foo.bar@example.com",
      "Foo",
      "Bar",
      "99999999",
      "12345678",
      "DEFAULT",
      uuid);

    given(this.companyRepository.findById(uuid)).willReturn(Optional.of(company));

    User user = this.createUserService.execute(createUserDTO);


    Assert.assertNotNull(user);
    Assert.assertEquals(user.getName(), "Foo");
    Assert.assertEquals(user.getLast_name(), "Bar");
    Assert.assertEquals(user.getEmail(), "foo.bar@example.com");
  }

}
