package com.samuelbraga.rotinabackend.dtos.user;

import com.samuelbraga.rotinabackend.models.Company;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

  private UUID id;
  private String email;
  private String name;
  private String lastName;
  private String phone;
  private Company company;
}
