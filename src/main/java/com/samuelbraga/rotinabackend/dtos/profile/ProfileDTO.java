package com.samuelbraga.rotinabackend.dtos.profile;

import com.samuelbraga.rotinabackend.enums.TypeUser;
import lombok.Data;

import java.util.UUID;

@Data
public class ProfileDTO {
  private UUID id;
  private TypeUser type;
}
