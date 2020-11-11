package com.samuelbraga.rotinabackend.modules.user.models;

import com.samuelbraga.rotinabackend.modules.user.enums.TypeUser;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Profile implements GrantedAuthority {
  private static final long serialVersionUID = 2405172041950251807L;

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;

  @Enumerated(EnumType.STRING)
  private TypeUser type;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public TypeUser getType() {
    return type;
  }

  public void setType(TypeUser type) {
    this.type = type;
  }

  @Override
  public String getAuthority() {
    return type.toString();
  }
}
