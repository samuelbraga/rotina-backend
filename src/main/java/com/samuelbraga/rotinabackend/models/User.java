package com.samuelbraga.rotinabackend.models;

import com.samuelbraga.rotinabackend.dtos.user.CreateUserDTO;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
public class User implements UserDetails {
  private static final long serialVersionUID = 1905122041950251207L;
  
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
  
  @ManyToOne
  private Company company;
  
  @ManyToMany(fetch = FetchType.EAGER)
  private List<Profile> profiles = new ArrayList<>();
  
  public User() {}

  public User(CreateUserDTO createUserDTO) {
    this.email = createUserDTO.getEmail();
    this.name = createUserDTO.getName();
    this.lastName = createUserDTO.getLastName();
    this.phone = createUserDTO.getPhone();
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
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public Company getCompany() {
    return company;
  }

  public void setCompany(Company company) {
    this.company = company;
  }

  public List<Profile> getProfiles() {
    return profiles;
  }

  public void setProfiles(List<Profile> profiles) {
    this.profiles = profiles;
  }
  
  public boolean isSuperAdmin() {
    for (Profile profile: this.profiles) {
      if(profile.getType().name().equals("SUPER_ADMIN")) {
        return true;
      }
    }

    return false;
  }

  public boolean isAdmin() {
    for (Profile profile: this.profiles) {
      if(profile.getType().name().equals("ADMIN")) {
        return true;
      }
    }

    return false;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return profiles;
  }

  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
