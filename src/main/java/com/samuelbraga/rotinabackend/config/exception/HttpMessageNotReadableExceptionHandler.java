package com.samuelbraga.rotinabackend.config.exception;

import com.samuelbraga.rotinabackend.config.exception.dto.HttpMessageNotReadableExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class HttpMessageNotReadableExceptionHandler {
  
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public HttpMessageNotReadableExceptionDTO handle(HttpMessageNotReadableException ex) {
    return new HttpMessageNotReadableExceptionDTO(Objects.requireNonNull(ex.getMessage()).split(";")[0]);
  }
}
