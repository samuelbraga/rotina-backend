package com.samuelbraga.rotinabackend.Modules.User.DTOS;

import com.samuelbraga.rotinabackend.Modules.User.Enums.TypeUser;
import com.samuelbraga.rotinabackend.Modules.Company.Models.Company;

import java.util.UUID;

public class UserDTO {

  private UUID id;
  private String email;
  private String name;
  private String last_name;
  private String phone;
  private String password;
  private TypeUser type;
  private Company company;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
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

  public TypeUser getType() {
    return type;
  }

  public void setType(TypeUser type) {
    this.type = type;
  }

  public Company getCompany() {
    return company;
  }

  public void setCompany(Company company) {
    this.company = company;
  }
}
