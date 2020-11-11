package com.samuelbraga.rotinabackend.modules.user.dtos.user;

import com.samuelbraga.rotinabackend.modules.company.models.Company;

import java.util.UUID;

public class UserDTO {

  private UUID id;
  private String email;
  private String name;
  private String lastName;
  private String phone;
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
  
  public Company getCompany() {
    return company;
  }

  public void setCompany(Company company) {
    this.company = company;
  }
}
