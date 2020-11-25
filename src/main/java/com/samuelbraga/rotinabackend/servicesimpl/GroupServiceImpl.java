package com.samuelbraga.rotinabackend.servicesimpl;

import com.samuelbraga.rotinabackend.dtos.group.CreateGroupDTO;
import com.samuelbraga.rotinabackend.dtos.group.GroupDTO;
import com.samuelbraga.rotinabackend.exceptions.BaseException;
import com.samuelbraga.rotinabackend.models.Company;
import com.samuelbraga.rotinabackend.models.Group;
import com.samuelbraga.rotinabackend.models.User;
import com.samuelbraga.rotinabackend.repositories.CompanyRepository;
import com.samuelbraga.rotinabackend.repositories.GroupRepository;
import com.samuelbraga.rotinabackend.repositories.UserRepository;
import com.samuelbraga.rotinabackend.services.GroupService;
import java.util.Optional;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {

  private final GroupRepository groupRepository;
  private final CompanyRepository companyRepository;
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public GroupServiceImpl(
    GroupRepository groupRepository,
    CompanyRepository companyRepository,
    UserRepository userRepository,
    ModelMapper modelMapper
  ) {
    this.groupRepository = groupRepository;
    this.companyRepository = companyRepository;
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public GroupDTO createGroup(CreateGroupDTO createGroupDTO, UUID userId) {
    this.verifyUserIsAuthorized(userId, createGroupDTO.getCompanyId());
    this.checkNameGroup(
        createGroupDTO.getName(),
        createGroupDTO.getCompanyId()
      );

    Company company = this.findCompany(createGroupDTO.getCompanyId());

    Group group = new Group();
    group.setName(createGroupDTO.getName());
    group.setCompany(company);

    this.groupRepository.save(group);

    return this.modelMapper.map(group, GroupDTO.class);
  }

  private void checkNameGroup(String name, UUID companyId) {
    Optional<Group> group = this.groupRepository.findByName(name);

    if (group.isPresent()) {
      throw new BaseException("Group already exists in your company");
    }
  }

  private Company findCompany(UUID companyId) {
    Optional<Company> company = this.companyRepository.findById(companyId);

    company.orElseThrow(() -> new BaseException("Company does not exists"));

    return company.get();
  }

  private User getUser(UUID userId) {
    Optional<User> user = this.userRepository.findById(userId);

    user.orElseThrow(() -> new BaseException("User does not exists"));

    return user.get();
  }

  private void verifyUserIsAuthorized(UUID userId, UUID companyId) {
    User user = this.getUser(userId);

    if (user.isSuperAdmin()) {
      return;
    }

    if (user.isAdmin() && user.getCompany().getId() == companyId) {
      return;
    }

    throw new BaseException("User does not permission");
  }
}
