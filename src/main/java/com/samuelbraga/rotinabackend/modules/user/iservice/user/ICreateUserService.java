package com.samuelbraga.rotinabackend.modules.user.iservice.user;

import com.samuelbraga.rotinabackend.modules.user.dtos.user.CreateUserDTO;
import com.samuelbraga.rotinabackend.modules.user.dtos.user.UserDTO;

import java.util.UUID;

public interface ICreateUserService {
  UserDTO execute(CreateUserDTO createUserDTO, UUID userId);
}
