package com.samuelbraga.rotinabackend.modules.user.models;

import com.samuelbraga.rotinabackend.modules.user.dtos.CreateUserDTO;
import com.samuelbraga.rotinabackend.modules.user.enums.TypeUser;
import com.samuelbraga.rotinabackend.modules.company.models.Company;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class User {
  
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;

  @Column(unique = true)
  private String email;
  
  private String name;
  
  private String lastName;
  
  private String phone;
  
  private String password;
  
  @Enumerated(EnumType.STRING)
  private TypeUser type;
  
  @ManyToOne
  private Company company;
  
  public User() {}

  public User(CreateUserDTO createUserDTO) {
    this.email = createUserDTO.getEmail();
    this.name = createUserDTO.getName();
    this.lastName = createUserDTO.getLastName();
    this.phone = createUserDTO.getPhone();
    this.type = TypeUser.valueOf(createUserDTO.getType());
    this.password = createUserDTO.getPassword();
  }

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
