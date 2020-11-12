package com.samuelbraga.rotinabackend.modules.user.controllers.user;

import com.samuelbraga.rotinabackend.modules.user.dtos.user.CreateUserDTO;
import com.samuelbraga.rotinabackend.modules.user.dtos.user.UserDTO;
import com.samuelbraga.rotinabackend.modules.user.iservice.user.ICreateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

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
  public UserDTO create(HttpServletRequest request, @RequestBody @Valid CreateUserDTO createUserDTO) {
    UUID userId = (UUID) request.getSession().getAttribute("userId");
    return this.createUserService.execute(createUserDTO, userId);
  }
}
