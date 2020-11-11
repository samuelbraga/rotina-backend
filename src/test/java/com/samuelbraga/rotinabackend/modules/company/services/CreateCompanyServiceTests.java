package com.samuelbraga.rotinabackend.modules.company.services;

import com.samuelbraga.rotinabackend.exceptions.BaseException;
import com.samuelbraga.rotinabackend.modules.company.dtos.CompanyDTO;
import com.samuelbraga.rotinabackend.modules.company.dtos.CreateCompanyDTO;
import com.samuelbraga.rotinabackend.modules.company.models.Company;
import com.samuelbraga.rotinabackend.modules.company.repositories.CompanyRepository;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class CreateCompanyServiceTests {
  @Mock
  private CompanyRepository companyRepository;

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private CreateCompanyService createCompanyService;
  
  @Test
  void itShouldBeCreatedNewCompany() {
    CreateCompanyDTO createCompanyDTO = new CreateCompanyDTO("foo");
    CompanyDTO companyDTO = new CompanyDTO();
    UUID userId = UUID.randomUUID();
    
    companyDTO.setName("foo");

    when(modelMapper.map(any(), any())).thenReturn(companyDTO);
    
    CompanyDTO result = this.createCompanyService.execute(createCompanyDTO, userId);

    Assert.assertNotNull(result);
    Assert.assertEquals("foo", result.getName());
  }

  @Test
  void itShouldNotBeCreatedCompanyWithCompanyNameExists() {
    Company company = new Company("foo");
    CreateCompanyDTO createCompanyDTO = new CreateCompanyDTO("foo");
    UUID userId = UUID.randomUUID();

    given(this.companyRepository.findByName(createCompanyDTO.getName())).willReturn(Optional.of(company));
    
    Assert.assertThrows(BaseException.class, () -> this.createCompanyService.execute(createCompanyDTO, userId));
    verify(this.companyRepository, never()).save(any(Company.class));
  }  
}
