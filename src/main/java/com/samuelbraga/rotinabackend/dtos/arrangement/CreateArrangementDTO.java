package com.samuelbraga.rotinabackend.dtos.arrangement;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateArrangementDTO {

  @NotNull
  @NotEmpty
  private String name;

  @NotNull
  private UUID companyId;
}
