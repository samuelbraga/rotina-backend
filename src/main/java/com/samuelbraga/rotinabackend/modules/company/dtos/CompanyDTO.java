package com.samuelbraga.rotinabackend.modules.company.dtos;

import java.util.UUID;

public class CompanyDTO {
  private UUID id;

  private String name;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
