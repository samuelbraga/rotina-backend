package com.samuelbraga.rotinabackend.Controllers.Companies;

import com.samuelbraga.rotinabackend.DTO.Companies.CompanyDTO;
import com.samuelbraga.rotinabackend.DTO.Companies.CreateCompanyDTO;
import com.samuelbraga.rotinabackend.Models.Company;
import com.samuelbraga.rotinabackend.Services.Companies.CreateCompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/companies")
public class CompanyController {
  
  private final CreateCompanyService createCompanyService;
  private final ModelMapper modelMapper;

  @Autowired
  public CompanyController(CreateCompanyService createCompanyService, ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
    this.createCompanyService = createCompanyService;
  }

  @PostMapping
  public CompanyDTO create(@RequestBody @Valid CreateCompanyDTO createCompanyDTO) {
    Company company = this.createCompanyService.execute(createCompanyDTO);
    CompanyDTO companyDTO = this.modelMapper.map(company, CompanyDTO.class);
    return companyDTO;
  }
}
