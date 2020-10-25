package com.samuelbraga.rotinabackend.Modules.User.IService;

import com.samuelbraga.rotinabackend.Modules.User.DTOS.CreateUserDTO;
import com.samuelbraga.rotinabackend.Modules.User.Models.User;

public interface ICreateUserService {
  User execute(CreateUserDTO createUserDTO);
}
