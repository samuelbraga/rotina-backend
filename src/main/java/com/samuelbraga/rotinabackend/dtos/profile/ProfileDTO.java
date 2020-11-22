package com.samuelbraga.rotinabackend.dtos.profile;

import com.samuelbraga.rotinabackend.enums.TypeUser;
import java.util.UUID;
import lombok.Data;

@Data
public class ProfileDTO {

  private UUID id;
  private TypeUser type;
}
