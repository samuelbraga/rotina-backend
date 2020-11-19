package com.samuelbraga.rotinabackend.config.exception;

import com.samuelbraga.rotinabackend.config.exception.dto.BaseExceptionDTO;
import com.samuelbraga.rotinabackend.exceptions.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExceptionHandler {
  
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BaseException.class)
  public BaseExceptionDTO handle(BaseException baseException) {
    return new BaseExceptionDTO(baseException.getMessage());
  }
}
