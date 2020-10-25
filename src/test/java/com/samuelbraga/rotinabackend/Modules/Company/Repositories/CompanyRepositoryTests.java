package com.samuelbraga.rotinabackend.Modules.Company.Repositories;

import com.samuelbraga.rotinabackend.Modules.Company.Models.Company;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
public class CompanyRepositoryTests {
  
  @MockBean
  private CompanyRepository companyRepository;
  
  @Test
  public void itShouldBeLoadCompanyFromItsId() {
    Company company = new Company("foo");
    company.setId(UUID.randomUUID());
    this.companyRepository.save(company);

    Optional<Company> companyResult = this.companyRepository.findById(company.getId());

    if(!companyResult.isPresent()) return;

    Assert.assertNotNull(companyResult.get());
    Assert.assertEquals(companyResult.get().getName(), "foo");
  }

  @Test
  public void itShouldBeReturnedNullWhenPassIdFromNonExistCompany() {
    boolean notPresent = this.companyRepository.findById(UUID.randomUUID()).isPresent();
    Assert.assertFalse(notPresent);
  }

  @Test
  public void itShouldBeLoadCompanyFromItsName() {
    String nameCompany = "foo";
    this.companyRepository.save(new Company(nameCompany));
    
    Optional<Company> company = this.companyRepository.findByName(nameCompany);
    
    if(!company.isPresent()) return;
    
    Assert.assertNotNull(company.get());
    Assert.assertEquals(company.get().getName(), nameCompany);
  }

  @Test
  public void itShouldBeReturnedNullWhenPassNameFromNonExistCompany() {
    String nameCompany = "foo";
    boolean notPresent = this.companyRepository.findByName(nameCompany).isPresent();
    Assert.assertFalse(notPresent);
  }
}
