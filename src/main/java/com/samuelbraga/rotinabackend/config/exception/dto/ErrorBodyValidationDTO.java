package com.samuelbraga.rotinabackend.config.exception.dto;

public class ErrorBodyValidationDTO {
  
  private final String field;
  private final String message;

  public ErrorBodyValidationDTO(String field, String message) {
    this.field = field;
    this.message = message;
  }

  public String getField() {
    return field;
  }

  public String getMessage() {
    return message;
  }
}
