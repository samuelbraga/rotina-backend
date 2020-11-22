package com.samuelbraga.rotinabackend.dtos.company;

import com.samuelbraga.rotinabackend.models.Image;
import java.util.UUID;
import lombok.Data;

@Data
public class CompanyDTO {

  private UUID id;
  private String name;
  private Image image;
}
