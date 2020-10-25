package com.samuelbraga.rotinabackend.Modules.User.Controllers;

import com.samuelbraga.rotinabackend.Modules.User.DTOS.CreateUserDTO;
import com.samuelbraga.rotinabackend.Modules.User.DTOS.UserDTO;
import com.samuelbraga.rotinabackend.Modules.User.IService.ICreateUserService;
import com.samuelbraga.rotinabackend.Modules.User.Models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
  
  private final ICreateUserService createUserService;
  private final ModelMapper modelMapper;
  
  @Autowired
  public UserController(ICreateUserService createUserService, ModelMapper modelMapper) {
    this.createUserService = createUserService;
    this.modelMapper = modelMapper;
  }
  
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserDTO create(@RequestBody @Valid CreateUserDTO createUserDTO) {
    User user = this.createUserService.execute(createUserDTO);
    return this.modelMapper.map(user, UserDTO.class);
  }
}
