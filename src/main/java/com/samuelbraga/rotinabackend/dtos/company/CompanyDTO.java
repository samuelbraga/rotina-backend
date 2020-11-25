package com.samuelbraga.rotinabackend.dtos.company;

import com.samuelbraga.rotinabackend.models.Image;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDTO {

  private UUID id;
  private String name;
  private Image image;
}
