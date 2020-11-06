package com.samuelbraga.rotinabackend.config.exception.dto;

public class BaseExceptionDTO {
  private final String message;

  public BaseExceptionDTO(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
