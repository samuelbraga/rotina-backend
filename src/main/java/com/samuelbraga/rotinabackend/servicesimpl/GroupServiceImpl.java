package com.samuelbraga.rotinabackend.servicesimpl;

import com.samuelbraga.rotinabackend.dtos.group.CreateGroupDTO;
import com.samuelbraga.rotinabackend.dtos.group.GroupDTO;
import com.samuelbraga.rotinabackend.exceptions.BaseException;
import com.samuelbraga.rotinabackend.models.Company;
import com.samuelbraga.rotinabackend.models.Groups;
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
    User user = this.getUser(userId);

    if (user.isCompanyAuthorized(createGroupDTO.getCompanyId())) {
      throw new BaseException("User does not permission");
    }

    this.checkNameGroup(createGroupDTO.getName());

    Company company = this.getCompany(createGroupDTO.getCompanyId());

    Groups groups = new Groups();
    groups.setName(createGroupDTO.getName());
    groups.setCompany(company);

    this.groupRepository.save(groups);

    return this.modelMapper.map(groups, GroupDTO.class);
  }

  private void checkNameGroup(String name) {
    Optional<Groups> group = this.groupRepository.findByName(name);

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
