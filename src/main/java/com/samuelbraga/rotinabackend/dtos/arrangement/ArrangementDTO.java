package com.samuelbraga.rotinabackend.dtos.arrangement;

import com.samuelbraga.rotinabackend.models.Company;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArrangementDTO {

  private UUID id;
  private String name;
  private Company company;
}
