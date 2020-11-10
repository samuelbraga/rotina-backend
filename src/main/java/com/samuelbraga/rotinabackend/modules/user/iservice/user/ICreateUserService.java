package com.samuelbraga.rotinabackend.modules.user.iservice.user;

import com.samuelbraga.rotinabackend.modules.user.dtos.user.CreateUserDTO;
import com.samuelbraga.rotinabackend.modules.user.dtos.user.UserDTO;

public interface ICreateUserService {
  UserDTO execute(CreateUserDTO createUserDTO);
}
