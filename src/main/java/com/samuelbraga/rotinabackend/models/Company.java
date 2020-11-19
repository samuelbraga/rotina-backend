package com.samuelbraga.rotinabackend.models;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
public class Company implements Serializable {

  private static final long serialVersionUID = -2338626292552177485L;
  
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;

  @Column(unique = true)
  private String name;
  
  public Company() {}
  
  public Company(String name) {
    this.name = name;
  }
}
