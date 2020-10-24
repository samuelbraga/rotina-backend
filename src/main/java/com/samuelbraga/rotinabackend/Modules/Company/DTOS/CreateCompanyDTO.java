package com.samuelbraga.rotinabackend.Modules.Company.DTOS;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CreateCompanyDTO {
  
  @NotNull @NotEmpty
  private String name;

  public CreateCompanyDTO() {
  }

  public CreateCompanyDTO(@NotNull @NotEmpty String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
