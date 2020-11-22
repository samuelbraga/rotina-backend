package com.samuelbraga.rotinabackend.services;

import com.samuelbraga.rotinabackend.dtos.profile.ProfileDTO;
import java.util.List;
import java.util.UUID;

public interface ProfileService {
  List<ProfileDTO> listProfiles(UUID userId);
}
