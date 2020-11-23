package com.samuelbraga.rotinabackend.models;

import com.samuelbraga.rotinabackend.dtos.group.CreateGroupDTO;
import java.util.UUID;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
public class Group {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;

  @Column(unique = true)
  private String name;

  @ManyToOne
  private Company company;
}
