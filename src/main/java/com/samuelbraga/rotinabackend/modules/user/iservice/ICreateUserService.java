package com.samuelbraga.rotinabackend.modules.user.iservice;

import com.samuelbraga.rotinabackend.modules.user.dtos.CreateUserDTO;
import com.samuelbraga.rotinabackend.modules.user.dtos.UserDTO;

public interface ICreateUserService {
  UserDTO execute(CreateUserDTO createUserDTO);
}
