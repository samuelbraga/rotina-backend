package com.samuelbraga.rotinabackend.models;

import com.samuelbraga.rotinabackend.dtos.user.CreateUserDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

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

  @ManyToOne(fetch = FetchType.LAZY)
  private Arrangement arrangement;

  @ManyToMany(fetch = FetchType.EAGER)
  private List<Profile> profiles = new ArrayList<>();

  public User(CreateUserDTO createUserDTO) {
    this.email = createUserDTO.getEmail();
    this.name = createUserDTO.getName();
    this.lastName = createUserDTO.getLastName();
    this.phone = createUserDTO.getPhone();
    this.password = createUserDTO.getPassword();
  }

  public boolean isSuperAdmin() {
    for (Profile profile : this.profiles) {
      if (profile.getType().name().equals("SUPER_ADMIN")) {
        return true;
      }
    }

    return false;
  }

  public boolean isAdmin() {
    for (Profile profile : this.profiles) {
      if (profile.getType().name().equals("ADMIN")) {
        return true;
      }
    }

    return false;
  }

  public boolean isCompanyAuthorized(UUID companyId) {
    return !isSuperAdmin() && (!isAdmin() || this.company.getId() != companyId);
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
