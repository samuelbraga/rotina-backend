package com.samuelbraga.rotinabackend.modules.user.controllers.session;

import com.samuelbraga.rotinabackend.modules.user.dtos.session.CreateSessionDTO;
import com.samuelbraga.rotinabackend.modules.user.dtos.session.TokenDTO;
import com.samuelbraga.rotinabackend.modules.user.iservice.session.ICreateSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/session")
public class SessionController {
  
  private final ICreateSessionService createSessionService;

  @Autowired
  public SessionController(ICreateSessionService createSessionService) {
    this.createSessionService = createSessionService;
  }
  
  @PostMapping
  public TokenDTO create(@RequestBody @Valid CreateSessionDTO createSessionDTO){
    return this.createSessionService.execute(createSessionDTO);
  }
}
