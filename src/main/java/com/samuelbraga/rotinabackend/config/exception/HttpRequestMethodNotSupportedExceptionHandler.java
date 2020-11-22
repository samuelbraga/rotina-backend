package com.samuelbraga.rotinabackend.config.exception;

import com.samuelbraga.rotinabackend.config.exception.dto.BaseExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HttpRequestMethodNotSupportedExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public BaseExceptionDTO handle(HttpRequestMethodNotSupportedException ex) {
    return new BaseExceptionDTO(ex.getMessage());
  }
}
