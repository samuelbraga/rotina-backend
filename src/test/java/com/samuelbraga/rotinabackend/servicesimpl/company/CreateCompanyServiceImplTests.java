package com.samuelbraga.rotinabackend.servicesimpl.company;

import static org.mockito.Mockito.*;

import com.samuelbraga.rotinabackend.config.aws.s3.AmazonS3ImageService;
import com.samuelbraga.rotinabackend.dtos.company.CompanyDTO;
import com.samuelbraga.rotinabackend.dtos.company.CreateCompanyDTO;
import com.samuelbraga.rotinabackend.enums.TypeUser;
import com.samuelbraga.rotinabackend.exceptions.BaseException;
import com.samuelbraga.rotinabackend.models.Company;
import com.samuelbraga.rotinabackend.models.Profile;
import com.samuelbraga.rotinabackend.models.User;
import com.samuelbraga.rotinabackend.repositories.CompanyRepository;
import com.samuelbraga.rotinabackend.repositories.UserRepository;
import com.samuelbraga.rotinabackend.servicesimpl.CompanyServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class CreateCompanyServiceImplTests {

  @Mock
  private CompanyRepository companyRepository;

  @Mock
  private UserRepository userRepository;

  @Mock
  private ModelMapper modelMapper;

  @Mock
  private AmazonS3ImageService amazonS3ImageService;

  @InjectMocks
  private CompanyServiceImpl companyService;
  
  private static User user;
  private static Company company;
  private static CreateCompanyDTO createCompanyDTO = new CreateCompanyDTO("foo");
  private static UUID companyId = UUID.randomUUID();
  private static UUID userId = UUID.randomUUID();
  
  static {
    company = Company.builder()
                .id(companyId)
                .name("foo")
                .build();

    Profile profile = new Profile();
    profile.setType(TypeUser.SUPER_ADMIN);
    List<Profile> profiles = new ArrayList<>();
    profiles.add(profile);

    user = User.builder()
            .id(userId)
            .email("foo.bar@example.com")
            .company(company)
            .profiles(profiles)
            .build();
  }
  
  @Before
  void init() {
    MockitoAnnotations.openMocks(this);
    this.companyService = new CompanyServiceImpl(this.companyRepository, this.userRepository, this.modelMapper, this.amazonS3ImageService);
  }
  
  @Test
  void itShouldBeCreatedNewCompany() {
    CompanyDTO companyDTO = CompanyDTO.builder().name("foo").build();
    
    when(this.modelMapper.map(any(), any())).thenReturn(companyDTO);
    when(this.userRepository.findById(this.userId)).thenReturn(Optional.of(this.user));

    CompanyDTO result =
      this.companyService.createCompany(this.createCompanyDTO, userId);

    Assert.assertNotNull(result);
    Assert.assertEquals("foo", result.getName());
  }

  @Test
  void itShouldNotBeCreatedCompanyWithCompanyNameExists() {
    when(this.userRepository.findById(this.userId)).thenReturn(Optional.of(this.user));
    when(this.companyRepository.findByName(this.createCompanyDTO.getName()))
      .thenReturn(Optional.of(this.company));

    Assert.assertThrows(
      BaseException.class,
      () -> this.companyService.createCompany(this.createCompanyDTO, userId)
    );
    verify(this.companyRepository, never()).save(any(Company.class));
  }

  @Test
  void itShouldNotBeCreatedCompanyWithNonExistsUser() {
    Assert.assertThrows(
      BaseException.class,
      () -> this.companyService.createCompany(this.createCompanyDTO, UUID.randomUUID())
    );
    verify(this.companyRepository, never()).save(any(Company.class));
  }

  @Test
  void itShouldNotBeCreatedCompanyWithUserNonAuthorized() {
    Profile profile = new Profile();
    profile.setType(TypeUser.ADMIN);
    List<Profile> profiles = new ArrayList<>();
    profiles.add(profile);
    
    User adminUser = User.builder()
      .id(this.userId)
      .email("foo.bar@example.com")
      .company(this.company)
      .profiles(profiles)
      .build();
    
    when(this.userRepository.findById(this.userId)).thenReturn(Optional.of(adminUser));

    Assert.assertThrows(
      BaseException.class,
      () -> this.companyService.createCompany(this.createCompanyDTO, this.userId)
    );
    verify(this.companyRepository, never()).save(any(Company.class));
  }
}
