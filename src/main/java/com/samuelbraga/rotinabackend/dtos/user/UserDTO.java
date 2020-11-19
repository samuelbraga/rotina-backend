package com.samuelbraga.rotinabackend.dtos.user;

import com.samuelbraga.rotinabackend.models.Company;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO {

  private UUID id;
  private String email;
  private String name;
  private String lastName;
  private String phone;
  private Company company;
}
