package com.samuelbraga.rotinabackend.Config.Exception;

import com.samuelbraga.rotinabackend.Config.Exception.DTO.BaseExceptionDTO;
import com.samuelbraga.rotinabackend.Exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExceptionHandler {
  
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({BaseException.class})
  public BaseExceptionDTO handle(BaseException baseException) {
    return new BaseExceptionDTO(baseException.getMessage());
  }
}
