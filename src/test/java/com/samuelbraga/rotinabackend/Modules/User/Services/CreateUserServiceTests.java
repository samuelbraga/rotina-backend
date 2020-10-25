package com.samuelbraga.rotinabackend.Modules.User.Services;

import com.samuelbraga.rotinabackend.Modules.Company.Models.Company;
import com.samuelbraga.rotinabackend.Modules.Company.Repositories.CompanyRepository;
import com.samuelbraga.rotinabackend.Modules.User.DTOS.CreateUserDTO;
import com.samuelbraga.rotinabackend.Modules.User.DTOS.UserDTO;
import com.samuelbraga.rotinabackend.Modules.User.Enums.TypeUser;
import com.samuelbraga.rotinabackend.Modules.User.Repositories.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class CreateUserServiceTests {
  @Mock
  private UserRepository userRepository;

  @Mock
  private CompanyRepository companyRepository;

  @Mock
  private ModelMapper modelMapper;
  
  @InjectMocks
  private CreateUserService createUserService;
  
  @Test
  public void itShouldBeCreatedNewUser() {
    UUID uuid = UUID.randomUUID();
    Company company = new Company("foo");
    
    CreateUserDTO createUserDTO = new CreateUserDTO("foo.bar@example.com",
      "Foo",
      "Bar",
      "99999999",
      "12345678",
      "DEFAULT",
      uuid);

    UserDTO userDTO = new UserDTO();
      
    userDTO.setEmail("foo.bar@example.com");
    userDTO.setName("Foo");
    userDTO.setLast_name("Bar");
    userDTO.setPhone("999999999");
    userDTO.setPassword("123456");
    userDTO.setType(TypeUser.DEFAULT);

    given(this.companyRepository.findById(uuid)).willReturn(Optional.of(company));
    when(modelMapper.map(any(), any())).thenReturn(userDTO);
    
    UserDTO result = this.createUserService.execute(createUserDTO);

    Assert.assertNotNull(result);
    Assert.assertEquals(result.getName(), "Foo");
    Assert.assertEquals(result.getLast_name(), "Bar");
    Assert.assertEquals(result.getEmail(), "foo.bar@example.com");
  }

}

