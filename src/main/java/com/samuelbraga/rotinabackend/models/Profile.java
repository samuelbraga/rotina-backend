package com.samuelbraga.rotinabackend.models;

import com.samuelbraga.rotinabackend.enums.TypeUser;
import java.util.UUID;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Data
public class Profile implements GrantedAuthority {

  private static final long serialVersionUID = 2405172041950251807L;

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;

  @Enumerated(EnumType.STRING)
  private TypeUser type;

  @Override
  public String getAuthority() {
    return type.toString();
  }
}
