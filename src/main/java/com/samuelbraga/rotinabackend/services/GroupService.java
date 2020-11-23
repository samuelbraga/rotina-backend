package com.samuelbraga.rotinabackend.services;

import com.samuelbraga.rotinabackend.dtos.group.CreateGroupDTO;
import com.samuelbraga.rotinabackend.dtos.group.GroupDTO;
import java.util.UUID;

public interface GroupService {
  GroupDTO createGroup(CreateGroupDTO createGroupDTO, UUID userId);
}
