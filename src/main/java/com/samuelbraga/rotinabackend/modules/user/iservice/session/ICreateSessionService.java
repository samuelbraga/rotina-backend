package com.samuelbraga.rotinabackend.modules.user.iservice.session;

import com.samuelbraga.rotinabackend.modules.user.dtos.session.CreateSessionDTO;
import com.samuelbraga.rotinabackend.modules.user.dtos.session.TokenDTO;

public interface ICreateSessionService {
  TokenDTO execute(CreateSessionDTO createSessionDTO);
}
