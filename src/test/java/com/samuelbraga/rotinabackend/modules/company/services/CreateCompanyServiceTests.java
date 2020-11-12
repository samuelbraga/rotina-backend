package com.samuelbraga.rotinabackend.modules.company.services;

import com.samuelbraga.rotinabackend.exceptions.BaseException;
import com.samuelbraga.rotinabackend.modules.company.dtos.CompanyDTO;
import com.samuelbraga.rotinabackend.modules.company.dtos.CreateCompanyDTO;
import com.samuelbraga.rotinabackend.modules.company.models.Company;
import com.samuelbraga.rotinabackend.modules.company.repositories.CompanyRepository;
import com.samuelbraga.rotinabackend.modules.user.dtos.user.UserDTO;
import com.samuelbraga.rotinabackend.modules.user.enums.TypeUser;
import com.samuelbraga.rotinabackend.modules.user.models.Profile;
import com.samuelbraga.rotinabackend.modules.user.models.User;
import com.samuelbraga.rotinabackend.modules.user.repositories.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class CreateCompanyServiceTests {
  @Mock
  private CompanyRepository companyRepository;
  
  @Mock
  private UserRepository userRepository;

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private CreateCompanyService createCompanyService;
  
  @Test
  void itShouldBeCreatedNewCompany() {
    CreateCompanyDTO createCompanyDTO = new CreateCompanyDTO("foo");
    CompanyDTO companyDTO = new CompanyDTO();
    companyDTO.setName("foo");

    Profile profile = new Profile();
    profile.setType(TypeUser.SUPER_ADMIN);
    List<Profile> profiles = new ArrayList<>();
    profiles.add(profile);
    
    UUID userId = UUID.randomUUID();

    User user = new User();
    user.setId(userId);
    user.setEmail("foo.bar@example.com");
    user.setName("Foo");
    user.setLastName("Bar");
    user.setPhone("999999999");
    user.setProfiles(profiles);
    
    when(modelMapper.map(any(), any())).thenReturn(companyDTO);
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    
    
    CompanyDTO result = this.createCompanyService.execute(createCompanyDTO, userId);

    Assert.assertNotNull(result);
    Assert.assertEquals("foo", result.getName());
  }

  @Test
  void itShouldNotBeCreatedCompanyWithCompanyNameExists() {
    Company company = new Company("foo");
    CreateCompanyDTO createCompanyDTO = new CreateCompanyDTO("foo");

    Profile profile = new Profile();
    profile.setType(TypeUser.SUPER_ADMIN);
    List<Profile> profiles = new ArrayList<>();
    profiles.add(profile);

    UUID userId = UUID.randomUUID();

    User user = new User();
    user.setId(userId);
    user.setEmail("foo.bar@example.com");
    user.setName("Foo");
    user.setLastName("Bar");
    user.setPhone("999999999");
    user.setProfiles(profiles);

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));    
    given(this.companyRepository.findByName(createCompanyDTO.getName())).willReturn(Optional.of(company));
    
    Assert.assertThrows(BaseException.class, () -> this.createCompanyService.execute(createCompanyDTO, userId));
    verify(this.companyRepository, never()).save(any(Company.class));
  }

  @Test
  void itShouldNotBeCreatedCompanyWithNonExistsUser() {
    CreateCompanyDTO createCompanyDTO = new CreateCompanyDTO("foo");
    
    UUID userId = UUID.randomUUID();
    
    Assert.assertThrows(BaseException.class, () -> this.createCompanyService.execute(createCompanyDTO, userId));
    verify(this.companyRepository, never()).save(any(Company.class));
  }

  @Test
  void itShouldNotBeCreatedCompanyWithUserNonAuthorized() {
    CreateCompanyDTO createCompanyDTO = new CreateCompanyDTO("foo");

    Profile profile = new Profile();
    profile.setType(TypeUser.ADMIN);
    List<Profile> profiles = new ArrayList<>();
    profiles.add(profile);

    UUID userId = UUID.randomUUID();

    User user = new User();
    user.setId(userId);
    user.setEmail("foo.bar@example.com");
    user.setName("Foo");
    user.setLastName("Bar");
    user.setPhone("999999999");
    user.setProfiles(profiles);

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    Assert.assertThrows(BaseException.class, () -> this.createCompanyService.execute(createCompanyDTO, userId));
    verify(this.companyRepository, never()).save(any(Company.class));
  }
}
