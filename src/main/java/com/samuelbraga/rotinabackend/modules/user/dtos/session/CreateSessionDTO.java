package com.samuelbraga.rotinabackend.modules.user.dtos.session;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CreateSessionDTO {
  @NotNull @NotEmpty @Email
  private String email;

  @NotNull @NotEmpty @Length
  private String password;
  
  public CreateSessionDTO(@NotNull @NotEmpty @Email String email, @NotNull @NotEmpty @Length String password) {
    this.email = email;
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
