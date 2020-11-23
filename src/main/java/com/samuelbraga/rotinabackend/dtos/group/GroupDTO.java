package com.samuelbraga.rotinabackend.dtos.group;

import com.samuelbraga.rotinabackend.models.Company;
import java.util.UUID;
import lombok.Data;

@Data
public class GroupDTO {

  private UUID id;
  private String name;
  private Company company;
}
