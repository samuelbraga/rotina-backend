package com.samuelbraga.rotinabackend.Modules.Company.Controllers;

import com.samuelbraga.rotinabackend.Modules.Company.DTOS.CompanyDTO;
import com.samuelbraga.rotinabackend.Modules.Company.DTOS.CreateCompanyDTO;
import com.samuelbraga.rotinabackend.Modules.Company.IService.ICreateCompanyService;
import com.samuelbraga.rotinabackend.Modules.Company.Models.Company;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/companies")
public class CompanyController {
  
  private final ICreateCompanyService createCompanyService;
  private final ModelMapper modelMapper;

  @Autowired
  public CompanyController(ICreateCompanyService createCompanyService, ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
    this.createCompanyService = createCompanyService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CompanyDTO create(@RequestBody @Valid CreateCompanyDTO createCompanyDTO) {
    Company company = this.createCompanyService.execute(createCompanyDTO);
    return this.modelMapper.map(company, CompanyDTO.class);
  }
}
