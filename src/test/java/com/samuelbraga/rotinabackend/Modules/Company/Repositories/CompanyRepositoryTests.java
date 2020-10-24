package com.samuelbraga.rotinabackend.Modules.Company.Repositories;

import com.samuelbraga.rotinabackend.Modules.Company.Models.Company;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class CompanyRepositoryTests {
  
  private final CompanyRepository companyRepository;

  @Autowired
  public CompanyRepositoryTests(CompanyRepository companyRepository) {
    this.companyRepository = companyRepository;
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
  public void itShouldBeReturnedNullWhenPassNonExistCompany() {
    String nameCompany = "foo";
    boolean notPresent = this.companyRepository.findByName(nameCompany).isPresent();
    Assert.assertFalse(notPresent);
  }
}
