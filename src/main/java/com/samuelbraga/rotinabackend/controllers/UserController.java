package com.samuelbraga.rotinabackend.controllers;

import com.samuelbraga.rotinabackend.services.UserService;
import com.samuelbraga.rotinabackend.dtos.user.CreateUserDTO;
import com.samuelbraga.rotinabackend.dtos.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserDTO create(HttpServletRequest request, @RequestBody @Valid CreateUserDTO createUserDTO) {
    UUID userId = (UUID) request.getSession().getAttribute("userId");
    return this.userService.createUser(createUserDTO, userId);
  }
}
