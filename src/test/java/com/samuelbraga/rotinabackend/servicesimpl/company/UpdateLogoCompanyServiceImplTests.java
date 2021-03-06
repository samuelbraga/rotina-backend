package com.samuelbraga.rotinabackend.servicesimpl.company;

import static org.mockito.Mockito.*;

import com.samuelbraga.rotinabackend.config.aws.s3.AmazonS3ImageService;
import com.samuelbraga.rotinabackend.dtos.company.CompanyDTO;
import com.samuelbraga.rotinabackend.enums.TypeUser;
import com.samuelbraga.rotinabackend.exceptions.BaseException;
import com.samuelbraga.rotinabackend.models.Company;
import com.samuelbraga.rotinabackend.models.Image;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class UpdateLogoCompanyServiceImplTests {

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
  private static final Image image;
  private static final CompanyDTO companyDTO;
  private static final UUID companyId = UUID.randomUUID();
  private static final UUID userId = UUID.randomUUID();
  private static final MockMultipartFile file;

  static {
    company = Company.builder().id(companyId).name("foo").build();

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

    image =
      Image.builder().id(UUID.randomUUID()).image_url("image_url").build();

    companyDTO =
      CompanyDTO.builder().id(companyId).name("foo").image(image).build();

    file =
      new MockMultipartFile(
        "file",
        "test.jpg",
        MediaType.TEXT_PLAIN_VALUE,
        "Test".getBytes()
      );
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
  void itShouldBeUpdateLogCompany() {
    when(this.userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(this.companyRepository.findById(companyId))
      .thenReturn(Optional.of(company));
    when(this.amazonS3ImageService.insertImages(file, companyId, "companies/"))
      .thenReturn(image);
    when(this.modelMapper.map(any(), any())).thenReturn(companyDTO);

    CompanyDTO result = this.companyService.updateLogo(file, companyId, userId);

    Assert.assertNotNull(result);
    Assert.assertEquals(image, result.getImage());
  }

  @Test
  void itShouldNotBeUpdateLogCompanyWithInvalidArchive() {
    MockMultipartFile fileIncorrect = new MockMultipartFile(
      "file",
      "test.jpg",
      MediaType.TEXT_PLAIN_VALUE,
      "Test".getBytes()
    );

    when(this.userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(this.companyRepository.findById(companyId))
      .thenReturn(Optional.of(company));
    when(
      this.amazonS3ImageService.insertImages(
          fileIncorrect,
          companyId,
          "companies/"
        )
    )
      .thenThrow(new BaseException("Image invalid format"));

    Assert.assertThrows(
      BaseException.class,
      () -> this.companyService.updateLogo(fileIncorrect, companyId, userId)
    );
    verify(this.companyRepository, never()).save(any(Company.class));
  }

  @Test
  void itShouldNotBeUpdateLogCompanyWithUserNonAuthorized() {
    Profile profile = Profile.builder().type(TypeUser.DEFAULT).build();
    List<Profile> profiles = new ArrayList<>();
    profiles.add(profile);

    User adminUser = User
      .builder()
      .id(userId)
      .email("foo.bar@example.com")
      .company(company)
      .profiles(profiles)
      .build();

    when(this.userRepository.findById(userId))
      .thenReturn(Optional.of(adminUser));

    Assert.assertThrows(
      BaseException.class,
      () -> this.companyService.updateLogo(file, companyId, userId)
    );
    verify(this.companyRepository, never()).save(any(Company.class));
  }

  @Test
  void itShouldNotBeUpdateLogCompanyWithNonExistsCompany() {
    when(this.userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(this.companyRepository.findById(companyId))
      .thenReturn(Optional.empty());

    Assert.assertThrows(
      BaseException.class,
      () -> this.companyService.updateLogo(file, companyId, userId)
    );
    verify(this.companyRepository, never()).save(any(Company.class));
  }
}
