package com.samuelbraga.rotinabackend.modules.user.controllers;

import com.samuelbraga.rotinabackend.modules.user.dtos.CreateUserDTO;
import com.samuelbraga.rotinabackend.modules.user.dtos.UserDTO;
import com.samuelbraga.rotinabackend.modules.user.iservice.ICreateUserService;
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
