package com.samuelbraga.rotinabackend.dtos.company;

import com.samuelbraga.rotinabackend.models.Image;
import lombok.Data;

import java.util.UUID;

@Data
public class CompanyDTO {
  private UUID id;
  private String name;
  private Image image;
}
