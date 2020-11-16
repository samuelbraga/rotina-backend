package com.samuelbraga.rotinabackend.iservices;

import com.samuelbraga.rotinabackend.dtos.session.CreateSessionDTO;
import com.samuelbraga.rotinabackend.dtos.session.TokenDTO;

public interface ISessionService {
  TokenDTO createSession(CreateSessionDTO createSessionDTO);
}
