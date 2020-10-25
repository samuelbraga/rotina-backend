package com.samuelbraga.rotinabackend.Modules.User.IService;

import com.samuelbraga.rotinabackend.Modules.User.DTOS.CreateUserDTO;
import com.samuelbraga.rotinabackend.Modules.User.DTOS.UserDTO;

public interface ICreateUserService {
  UserDTO execute(CreateUserDTO createUserDTO);
}
