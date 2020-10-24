package com.samuelbraga.rotinabackend.Config.Exception.DTO;

public class BaseExceptionDTO {
  private String message;

  public BaseExceptionDTO(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
