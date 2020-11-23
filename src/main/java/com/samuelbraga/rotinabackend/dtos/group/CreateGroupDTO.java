package com.samuelbraga.rotinabackend.dtos.group;

import java.util.UUID;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateGroupDTO {

  @NotNull
  @NotEmpty
  private String name;

  @NotNull
  private UUID companyId;
}
