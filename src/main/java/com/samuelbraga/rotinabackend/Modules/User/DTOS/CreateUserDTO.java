package com.samuelbraga.rotinabackend.Modules.User.DTOS;

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
  private String last_name;

  @NotNull @NotEmpty
  private String phone;

  @NotNull @NotEmpty @Length
  private String password;

  @NotNull @NotEmpty
  private String type;

  @NotNull
  private UUID company_id;

  public CreateUserDTO() {
  }

  public CreateUserDTO(@NotNull @NotEmpty @Email String email,
                       @NotNull @NotEmpty String name,
                       @NotNull @NotEmpty String last_name,
                       @NotNull @NotEmpty String phone,
                       @NotNull @NotEmpty @Length String password,
                       @NotNull @NotEmpty String type,
                       @NotNull UUID company_id) {
    this.email = email;
    this.name = name;
    this.last_name = last_name;
    this.phone = phone;
    this.password = password;
    this.type = type;
    this.company_id = company_id;
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

  public String getLast_name() {
    return last_name;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public UUID getCompany_id() {
    return company_id;
  }

  public void setCompany_id(UUID company_id) {
    this.company_id = company_id;
  }
}
