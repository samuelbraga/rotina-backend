package com.samuelbraga.rotinabackend.DTO.Companies;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CreateCompanyDTO {
  
  @NotNull @NotEmpty
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
