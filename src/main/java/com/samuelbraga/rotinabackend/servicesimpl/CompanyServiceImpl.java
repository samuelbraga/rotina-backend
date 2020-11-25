package com.samuelbraga.rotinabackend.servicesimpl;

import com.samuelbraga.rotinabackend.config.aws.s3.AmazonS3ImageService;
import com.samuelbraga.rotinabackend.dtos.company.CompanyDTO;
import com.samuelbraga.rotinabackend.dtos.company.CreateCompanyDTO;
import com.samuelbraga.rotinabackend.exceptions.BaseException;
import com.samuelbraga.rotinabackend.models.Company;
import com.samuelbraga.rotinabackend.models.Image;
import com.samuelbraga.rotinabackend.models.User;
import com.samuelbraga.rotinabackend.repositories.CompanyRepository;
import com.samuelbraga.rotinabackend.repositories.UserRepository;
import com.samuelbraga.rotinabackend.services.CompanyService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CompanyServiceImpl implements CompanyService {

  private final CompanyRepository companyRepository;
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  private final AmazonS3ImageService amazonS3ImageService;

  @Autowired
  public CompanyServiceImpl(
    CompanyRepository companyRepository,
    UserRepository userRepository,
    ModelMapper modelMapper,
    AmazonS3ImageService amazonS3ImageService
  ) {
    this.companyRepository = companyRepository;
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
    this.amazonS3ImageService = amazonS3ImageService;
  }

  @Override
  @PreAuthorize("hasRole(ROLE_SUPER_ADMIN)")
  public CompanyDTO createCompany(
    CreateCompanyDTO createCompanyDTO,
    UUID userId
  ) {
    User user = this.getUser(userId);
    
    Optional<Company> companyExists =
      this.companyRepository.findByName(createCompanyDTO.getName());

    if (companyExists.isPresent()) {
      throw new BaseException("Company already exists");
    }

    Company company = new Company(createCompanyDTO.getName());
    this.companyRepository.save(company);

    return this.modelMapper.map(company, CompanyDTO.class);
  }

  @Override
  public List<CompanyDTO> listCompanies(UUID userId) {
    User user = this.getUser(userId);

    if (!user.isSuperAdmin()) {
      throw new BaseException("User does not permission");
    }

    List<Company> listCompanies = this.companyRepository.findAll();
    return listCompanies
      .stream()
      .map(company -> this.modelMapper.map(company, CompanyDTO.class))
      .collect(Collectors.toList());
  }

  @Override
  public CompanyDTO updateLogo(
    MultipartFile multipartFile,
    UUID companyId,
    UUID userId
  ) {
    User user = this.getUser(userId);

    if (!user.isSuperAdmin()) {
      throw new BaseException("User does not permission");
    }

    if (!user.isAdmin() && user.getCompany().getId() == companyId) {
      throw new BaseException("User does not permission");
    }

    Company company = this.getCompany(companyId);

    Image image =
      this.amazonS3ImageService.insertImages(
          multipartFile,
          companyId,
          "companies/"
        );
    company.setImage(image);

    this.companyRepository.save(company);
    return this.modelMapper.map(company, CompanyDTO.class);
  }

  private User getUser(UUID userId) {
    Optional<User> user = this.userRepository.findById(userId);

    user.orElseThrow(() -> new BaseException("User does not exists"));

    return user.get();
  }

  private Company getCompany(UUID companyId) {
    Optional<Company> company = this.companyRepository.findById(companyId);

    company.orElseThrow(() -> new BaseException("Company does not exists"));

    return company.get();
  }
}
