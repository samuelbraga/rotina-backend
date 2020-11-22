package com.samuelbraga.rotinabackend.services;

import com.samuelbraga.rotinabackend.dtos.company.CompanyDTO;
import com.samuelbraga.rotinabackend.dtos.company.CreateCompanyDTO;
import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface CompanyService {
  CompanyDTO createCompany(CreateCompanyDTO createCompanyDTO, UUID userId);
  CompanyDTO updateLogo(
    MultipartFile multipartFile,
    UUID companyId,
    UUID userId
  );
  List<CompanyDTO> listCompanies(UUID userId);
}
