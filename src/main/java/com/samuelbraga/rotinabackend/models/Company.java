package com.samuelbraga.rotinabackend.models;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
public class Company {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;

  @Column(unique = true)
  private String name;

  @OneToOne
  private Image image;

  public Company() {}

  public Company(String name) {
    this.name = name;
  }
}
