package com.samuelbraga.rotinabackend.modules.user.services;

import com.samuelbraga.rotinabackend.config.Hash.Hash;
import com.samuelbraga.rotinabackend.modules.company.models.Company;
import com.samuelbraga.rotinabackend.modules.company.repositories.CompanyRepository;
import com.samuelbraga.rotinabackend.modules.user.dtos.CreateUserDTO;
import com.samuelbraga.rotinabackend.modules.user.dtos.UserDTO;
import com.samuelbraga.rotinabackend.modules.user.enums.TypeUser;
import com.samuelbraga.rotinabackend.modules.user.repositories.UserRepository;
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
class CreateUserServiceTests {
  @Mock
  private UserRepository userRepository;

  @Mock
  private CompanyRepository companyRepository;

  @Mock
  private ModelMapper modelMapper;

  @Mock
  private Hash hash;
  
  @InjectMocks
  private CreateUserService createUserService;
  
  @Test
  void itShouldBeCreatedNewUser() {
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
    userDTO.setLastName("Bar");
    userDTO.setPhone("999999999");
    userDTO.setType(TypeUser.DEFAULT);

    given(this.companyRepository.findById(uuid)).willReturn(Optional.of(company));
    when(modelMapper.map(any(), any())).thenReturn(userDTO);
    
    UserDTO result = this.createUserService.execute(createUserDTO);

    Assert.assertNotNull(result);
    Assert.assertEquals("Foo", result.getName());
    Assert.assertEquals("Bar", result.getLastName());
    Assert.assertEquals("foo.bar@example.com", result.getEmail());
  }

}

