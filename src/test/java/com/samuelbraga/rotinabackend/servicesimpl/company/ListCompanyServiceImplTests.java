package com.samuelbraga.rotinabackend.servicesimpl.company;

import static org.mockito.Mockito.*;

import com.samuelbraga.rotinabackend.config.aws.s3.AmazonS3ImageService;
import com.samuelbraga.rotinabackend.dtos.company.CompanyDTO;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class ListCompanyServiceImplTests {

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

  private static final User user;
  private static final Company company;
  private static final UUID userId = UUID.randomUUID();

  static {
    company = Company.builder().id(UUID.randomUUID()).name("foo").build();

    Profile profile = Profile.builder().type(TypeUser.SUPER_ADMIN).build();
    List<Profile> profiles = new ArrayList<>();
    profiles.add(profile);

    user =
      User
        .builder()
        .id(userId)
        .email("foo.bar@example.com")
        .company(company)
        .profiles(profiles)
        .build();
  }

  @BeforeEach
  void init() {
    MockitoAnnotations.openMocks(this);
    this.companyService =
      new CompanyServiceImpl(
        this.companyRepository,
        this.userRepository,
        this.modelMapper,
        this.amazonS3ImageService
      );
  }

  @Test
  void itShouldBeListCompanies() {
    List<Company> listCompanies = new ArrayList<>();
    listCompanies.add(new Company());
    listCompanies.add(new Company());

    CompanyDTO companyDTO = CompanyDTO.builder().name("foo").build();

    when(this.userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(this.companyRepository.findAll()).thenReturn(listCompanies);
    when(this.modelMapper.map(any(), any())).thenReturn(companyDTO);

    List<CompanyDTO> result = this.companyService.listCompanies(userId);

    Assert.assertNotNull(result);
    Assert.assertEquals(2, result.size());
  }

  @Test
  void itShouldNotBeListCompanies() {
    Profile profile = Profile.builder().type(TypeUser.ADMIN).build();
    List<Profile> profiles = new ArrayList<>();
    profiles.add(profile);

    User adminUser = User
      .builder()
      .id(userId)
      .email("foo.bar@example.com")
      .company(company)
      .profiles(profiles)
      .build();

    List<Company> listCompanies = new ArrayList<>();
    listCompanies.add(new Company());
    listCompanies.add(new Company());

    CompanyDTO companyDTO = CompanyDTO.builder().name("foo").build();

    when(this.userRepository.findById(userId))
      .thenReturn(Optional.of(adminUser));
    when(this.companyRepository.findAll()).thenReturn(listCompanies);
    when(this.modelMapper.map(any(), any())).thenReturn(companyDTO);

    Assert.assertThrows(
      BaseException.class,
      () -> this.companyService.listCompanies(userId)
    );
    verify(this.companyRepository, never()).findAll();
  }
}
