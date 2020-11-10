package com.samuelbraga.rotinabackend.modules.user.dtos.user;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

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
  private UUID companyId;

  public CreateUserDTO() {
  }

  public CreateUserDTO(@NotNull @NotEmpty @Email String email,
                       @NotNull @NotEmpty String name,
                       @NotNull @NotEmpty String lastName,
                       @NotNull @NotEmpty String phone,
                       @NotNull @NotEmpty @Length String password,
                       @NotNull @NotEmpty UUID companyId) {
    this.email = email;
    this.name = name;
    this.lastName = lastName;
    this.phone = phone;
    this.password = password;
    this.companyId = companyId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
  
  public UUID getCompanyId() {
    return companyId;
  }

  public void setCompanyId(UUID companyId) {
    this.companyId = companyId;
  }
}
