package com.samuelbraga.rotinabackend.config.exception.dto;

import lombok.Data;

@Data
public class BaseExceptionDTO {
  private final String message;

  public BaseExceptionDTO(String message) {
    this.message = message;
  }
}
