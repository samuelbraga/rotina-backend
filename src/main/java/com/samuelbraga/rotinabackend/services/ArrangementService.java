package com.samuelbraga.rotinabackend.services;

import com.samuelbraga.rotinabackend.dtos.arrangement.ArrangementDTO;
import com.samuelbraga.rotinabackend.dtos.arrangement.CreateArrangementDTO;
import java.util.List;
import java.util.UUID;

public interface ArrangementService {
  ArrangementDTO createGroup(
    CreateArrangementDTO createArrangementDTO,
    UUID userId,
    UUID companyId
  );
  List<ArrangementDTO> listGroups(UUID userId, UUID companyId);
}
