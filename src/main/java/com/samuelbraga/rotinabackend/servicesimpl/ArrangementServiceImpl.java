package com.samuelbraga.rotinabackend.servicesimpl;

import com.samuelbraga.rotinabackend.dtos.arrangement.ArrangementDTO;
import com.samuelbraga.rotinabackend.dtos.arrangement.CreateArrangementDTO;
import com.samuelbraga.rotinabackend.exceptions.BaseException;
import com.samuelbraga.rotinabackend.models.Arrangement;
import com.samuelbraga.rotinabackend.models.Company;
import com.samuelbraga.rotinabackend.models.User;
import com.samuelbraga.rotinabackend.repositories.ArrangementRepository;
import com.samuelbraga.rotinabackend.repositories.CompanyRepository;
import com.samuelbraga.rotinabackend.repositories.UserRepository;
import com.samuelbraga.rotinabackend.services.ArrangementService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArrangementServiceImpl implements ArrangementService {

  private final ArrangementRepository arrangementRepository;
  private final CompanyRepository companyRepository;
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public ArrangementServiceImpl(
    ArrangementRepository arrangementRepository,
    CompanyRepository companyRepository,
    UserRepository userRepository,
    ModelMapper modelMapper
  ) {
    this.arrangementRepository = arrangementRepository;
    this.companyRepository = companyRepository;
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public ArrangementDTO createGroup(
    CreateArrangementDTO createArrangementDTO,
    UUID userId,
    UUID companyId
  ) {
    User user = this.getUser(userId);

    if (user.isCompanyAuthorized(companyId)) {
      throw new BaseException("User does not permission");
    }

    this.checkNameGroupExists(createArrangementDTO.getName(), companyId);

    Company company = this.getCompany(companyId);

    Arrangement arrangement = Arrangement
      .builder()
      .name(createArrangementDTO.getName())
      .company(company)
      .build();

    this.arrangementRepository.save(arrangement);

    return this.modelMapper.map(arrangement, ArrangementDTO.class);
  }

  @Override
  public List<ArrangementDTO> listGroups(UUID userId, UUID companyId) {
    User user = this.getUser(userId);

    if (user.isCompanyAuthorized(companyId)) {
      throw new BaseException("User does not permission");
    }

    this.getCompany(companyId);

    List<Arrangement> arrangementList = this.arrangementRepository.findAll();
    return arrangementList
      .stream()
      .map(
        arrangement -> this.modelMapper.map(arrangement, ArrangementDTO.class)
      )
      .collect(Collectors.toList());
  }

  private void checkNameGroupExists(String name, UUID companyId) {
    Optional<Arrangement> group =
      this.arrangementRepository.findByNameAndCompany_Id(name, companyId);

    if (group.isPresent()) {
      throw new BaseException("Group already exists in your company");
    }
  }

  private Company getCompany(UUID companyId) {
    Optional<Company> company = this.companyRepository.findById(companyId);

    company.orElseThrow(() -> new BaseException("Company does not exists"));

    return company.get();
  }

  private User getUser(UUID userId) {
    Optional<User> user = this.userRepository.findById(userId);

    user.orElseThrow(() -> new BaseException("User does not exists"));

    return user.get();
  }
}
