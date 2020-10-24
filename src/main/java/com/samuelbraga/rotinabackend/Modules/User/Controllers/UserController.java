package com.samuelbraga.rotinabackend.Modules.User.Controllers;

import com.samuelbraga.rotinabackend.Modules.User.DTOS.CreateUserDTO;
import com.samuelbraga.rotinabackend.Modules.User.DTOS.UserDTO;
import com.samuelbraga.rotinabackend.Modules.User.Models.User;
import com.samuelbraga.rotinabackend.Modules.User.Services.CreateUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
  
  private final CreateUserService createUserService;
  private final ModelMapper modelMapper;
  
  @Autowired
  public UserController(CreateUserService createUserService, ModelMapper modelMapper) {
    this.createUserService = createUserService;
    this.modelMapper = modelMapper;
  }
  
  @PostMapping
  public UserDTO create(@RequestBody @Valid CreateUserDTO createUserDTO) {
    User user = this.createUserService.execute(createUserDTO);
    return this.modelMapper.map(user, UserDTO.class);
  }
}
