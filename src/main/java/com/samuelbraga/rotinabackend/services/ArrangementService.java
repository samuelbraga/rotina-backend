package com.samuelbraga.rotinabackend.services;

import com.samuelbraga.rotinabackend.dtos.arrangement.ArrangementDTO;
import com.samuelbraga.rotinabackend.dtos.arrangement.CreateArrangementDTO;
import com.samuelbraga.rotinabackend.dtos.user.UserDTO;

import java.util.List;
import java.util.UUID;

public interface ArrangementService {
  ArrangementDTO createGroup(
    CreateArrangementDTO createArrangementDTO,
    UUID userId
  );
  List<ArrangementDTO> listGroupsByCompany(UUID userId, UUID companyId);
  List<UserDTO> linkGroupsToUsers(UUID userId, UUID companyId, List<UUID> usersId);
}
