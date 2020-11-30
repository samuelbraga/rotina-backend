package com.samuelbraga.rotinabackend.services;

import com.samuelbraga.rotinabackend.dtos.user.CreateUserDTO;
import com.samuelbraga.rotinabackend.dtos.user.UserDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
  UserDTO createUser(CreateUserDTO createUserDTO, UUID userId);
  List<UserDTO> listUsers(UUID userId);
}
