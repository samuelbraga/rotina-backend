package com.samuelbraga.rotinabackend.controllers;

import com.samuelbraga.rotinabackend.dtos.session.CreateSessionDTO;
import com.samuelbraga.rotinabackend.dtos.session.TokenDTO;
import com.samuelbraga.rotinabackend.iservices.ISessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/session")
public class SessionController {
  
  private final ISessionService sessionService;

  @Autowired
  public SessionController(ISessionService sessionService) {
    this.sessionService = sessionService;
  }
  
  @PostMapping
  public TokenDTO create(@RequestBody @Valid CreateSessionDTO createSessionDTO){
    return this.sessionService.createSession(createSessionDTO);
  }
}
