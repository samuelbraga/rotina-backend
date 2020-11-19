package com.samuelbraga.rotinabackend.dtos.company;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateCompanyDTO {
  
  @NotNull @NotEmpty
  private String name;

  public CreateCompanyDTO() { }

  public CreateCompanyDTO(@NotNull @NotEmpty String name) {
    this.name = name;
  }
}
