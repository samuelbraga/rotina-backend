package com.samuelbraga.rotinabackend.services;

import com.samuelbraga.rotinabackend.dtos.session.CreateSessionDTO;
import com.samuelbraga.rotinabackend.dtos.session.TokenDTO;

public interface SessionService {
  TokenDTO createSession(CreateSessionDTO createSessionDTO);
}
