package com.samuelbraga.rotinabackend.controllers;

import com.samuelbraga.rotinabackend.dtos.company.CompanyDTO;
import com.samuelbraga.rotinabackend.dtos.company.CreateCompanyDTO;
import com.samuelbraga.rotinabackend.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/companies")
public class CompanyController {
  
  private final CompanyService createCompanyService;

  @Autowired
  public CompanyController(CompanyService createCompanyService) {
    this.createCompanyService = createCompanyService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CompanyDTO create(HttpServletRequest request, @RequestBody @Valid CreateCompanyDTO createCompanyDTO) {
    UUID userId = (UUID) request.getSession().getAttribute("userId");
    return this.createCompanyService.createCompany(createCompanyDTO, userId);
  }
}
