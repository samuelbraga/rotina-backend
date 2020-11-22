package com.samuelbraga.rotinabackend.servicesimpl;

import com.samuelbraga.rotinabackend.dtos.profile.ProfileDTO;
import com.samuelbraga.rotinabackend.exceptions.BaseException;
import com.samuelbraga.rotinabackend.models.Profile;
import com.samuelbraga.rotinabackend.models.User;
import com.samuelbraga.rotinabackend.repositories.ProfileRepository;
import com.samuelbraga.rotinabackend.repositories.UserRepository;
import com.samuelbraga.rotinabackend.services.ProfileService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

  private ProfileRepository profileRepository;
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public ProfileServiceImpl(
    ProfileRepository profileRepository,
    UserRepository userRepository,
    ModelMapper modelMapper
  ) {
    this.profileRepository = profileRepository;
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<ProfileDTO> listProfiles(UUID userId) {
    User user = this.getUser(userId);

    if (!user.isSuperAdmin()) {
      throw new BaseException("User does not permission");
    }

    List<Profile> listProfiles = this.profileRepository.findAll();
    return listProfiles
      .stream()
      .map(profile -> this.modelMapper.map(profile, ProfileDTO.class))
      .collect(Collectors.toList());
  }

  private User getUser(UUID userId) {
    Optional<User> user = this.userRepository.findById(userId);

    user.orElseThrow(() -> new BaseException("User does not exists"));

    return user.get();
  }
}
