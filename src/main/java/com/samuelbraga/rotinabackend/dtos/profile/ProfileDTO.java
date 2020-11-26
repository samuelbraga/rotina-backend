package com.samuelbraga.rotinabackend.dtos.profile;

import com.samuelbraga.rotinabackend.enums.TypeUser;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {

  private UUID id;
  private TypeUser type;
}
