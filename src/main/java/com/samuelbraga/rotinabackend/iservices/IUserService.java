package com.samuelbraga.rotinabackend.iservices;

import com.samuelbraga.rotinabackend.dtos.user.CreateUserDTO;
import com.samuelbraga.rotinabackend.dtos.user.UserDTO;

import java.util.UUID;

public interface IUserService {
  UserDTO createUser(CreateUserDTO createUserDTO, UUID userId);
}
