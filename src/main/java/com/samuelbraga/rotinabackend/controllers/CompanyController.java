package com.samuelbraga.rotinabackend.controllers;

import com.samuelbraga.rotinabackend.dtos.company.CompanyDTO;
import com.samuelbraga.rotinabackend.dtos.company.CreateCompanyDTO;
import com.samuelbraga.rotinabackend.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/companies")
public class CompanyController {
  
  private final CompanyService companyService;

  @Autowired
  public CompanyController(CompanyService companyService) {
    this.companyService = companyService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CompanyDTO create(HttpServletRequest request, @RequestBody @Valid CreateCompanyDTO createCompanyDTO) {
    UUID userId = (UUID) request.getSession().getAttribute("userId");
    return this.companyService.createCompany(createCompanyDTO, userId);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<CompanyDTO> list(HttpServletRequest request) {
    UUID userId = (UUID) request.getSession().getAttribute("userId");
    return this.companyService.listCompanies(userId);
  }

  @PutMapping(path = "/{companyId}/logo")
  @ResponseStatus(HttpStatus.OK)
  public CompanyDTO updateLogo(HttpServletRequest request, @PathVariable UUID companyId, @RequestPart(value = "logo") MultipartFile multipartFile) {
    UUID userId = (UUID) request.getSession().getAttribute("userId");
    return this.companyService.updateLogo(multipartFile, companyId, userId);
  }
}
