package com.samuelbraga.rotinabackend.Config.Exception.DTO;

public class ErrorBodyValidationDTO {
  
  private String field;
  private String message;

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
