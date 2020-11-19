package com.samuelbraga.rotinabackend.dtos.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class CreateUserDTO {
  
  @NotNull @NotEmpty @Email
  private String email;

  @NotNull @NotEmpty
  private String name;
  
  @NotNull @NotEmpty
  private String lastName;

  @NotNull @NotEmpty
  private String phone;

  @NotNull @NotEmpty @Length
  private String password;
  
  @NotNull
  private UUID profileId;
  
  @NotNull
  private UUID companyId;
  
  public CreateUserDTO() {
  }

  public CreateUserDTO(@NotNull @NotEmpty @Email String email,
                       @NotNull @NotEmpty String name,
                       @NotNull @NotEmpty String lastName,
                       @NotNull @NotEmpty String phone,
                       @NotNull @NotEmpty @Length String password,
                       @NotNull UUID profileId,
                       @NotNull UUID companyId) {
    this.email = email;
    this.name = name;
    this.lastName = lastName;
    this.phone = phone;
    this.password = password;
    this.profileId = profileId;
    this.companyId = companyId;
  }
}
