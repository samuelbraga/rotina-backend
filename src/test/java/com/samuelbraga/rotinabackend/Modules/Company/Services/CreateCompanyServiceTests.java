package com.samuelbraga.rotinabackend.Modules.Company.Services;

import com.samuelbraga.rotinabackend.Exceptions.BaseException;
import com.samuelbraga.rotinabackend.Modules.Company.DTOS.CreateCompanyDTO;
import com.samuelbraga.rotinabackend.Modules.Company.Models.Company;
import com.samuelbraga.rotinabackend.Modules.Company.Repositories.CompanyRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class CreateCompanyServiceTests {
  @Mock
  private CompanyRepository companyRepository;

  @InjectMocks
  private CreateCompanyService createCompanyService;
  
  @Test
  public void itShouldBeCreatedNewCompany() {
    CreateCompanyDTO createCompanyDTO = new CreateCompanyDTO("foo");
    
    Company company = this.createCompanyService.execute(createCompanyDTO);

    Assert.assertNotNull(company);
    Assert.assertEquals(company.getName(), "foo");
  }

  @Test
  public void itShouldNotBeCreatedCompanyWithCompanyNameExists() {
    Company company = new Company("foo");
    CreateCompanyDTO createCompanyDTO = new CreateCompanyDTO("foo");

    given(this.companyRepository.findByName(createCompanyDTO.getName())).willReturn(Optional.of(company));
    
    Assert.assertThrows(BaseException.class, () -> this.createCompanyService.execute(createCompanyDTO));
    verify(this.companyRepository, never()).save(any(Company.class));
  }  
}
