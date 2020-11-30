package com.samuelbraga.rotinabackend.controllers;

import com.samuelbraga.rotinabackend.dtos.user.CreateUserDTO;
import com.samuelbraga.rotinabackend.dtos.user.UserDTO;
import com.samuelbraga.rotinabackend.services.UserService;

import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
  public UserDTO create(
    HttpServletRequest request,
    @RequestBody @Valid CreateUserDTO createUserDTO
  ) {
    UUID userId = (UUID) request.getSession().getAttribute("userId");
    return this.userService.createUser(createUserDTO, userId);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<UserDTO> list(
    HttpServletRequest request
  ) {
    UUID userId = (UUID) request.getSession().getAttribute("userId");
    return this.userService.listUsers(userId);
  }
}
