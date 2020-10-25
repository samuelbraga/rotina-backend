package com.samuelbraga.rotinabackend.Modules.User.Controllers;

import com.samuelbraga.rotinabackend.Modules.User.DTOS.CreateUserDTO;
import com.samuelbraga.rotinabackend.Modules.User.DTOS.UserDTO;
import com.samuelbraga.rotinabackend.Modules.User.IService.ICreateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
  
  private final ICreateUserService createUserService;
    
  @Autowired
  public UserController(ICreateUserService createUserService) {
    this.createUserService = createUserService;
  }
  
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserDTO create(@RequestBody @Valid CreateUserDTO createUserDTO) {
    return this.createUserService.execute(createUserDTO);
  }
}
