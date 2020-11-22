package com.samuelbraga.rotinabackend.dtos.company;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCompanyDTO {

  @NotNull
  @NotEmpty
  private String name;

  public CreateCompanyDTO() {}

  public CreateCompanyDTO(@NotNull @NotEmpty String name) {
    this.name = name;
  }
}
