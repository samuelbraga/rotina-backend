package com.samuelbraga.rotinabackend.dtos.session;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CreateSessionDTO {

  @NotNull
  @NotEmpty
  @Email
  private String email;

  @NotNull
  @NotEmpty
  @Length
  private String password;

  public CreateSessionDTO(
    @NotNull @NotEmpty @Email String email,
    @NotNull @NotEmpty @Length String password
  ) {
    this.email = email;
    this.password = password;
  }
}
