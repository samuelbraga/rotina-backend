package com.samuelbraga.rotinabackend.dtos.user;

import com.samuelbraga.rotinabackend.models.Company;
import java.util.UUID;
import lombok.Data;

@Data
public class UserDTO {

  private UUID id;
  private String email;
  private String name;
  private String lastName;
  private String phone;
  private Company company;
}
