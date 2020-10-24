package com.samuelbraga.rotinabackend.Config.Exception.DTO;

public class BaseExceptionDTO {
  private final String message;

  public BaseExceptionDTO(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
