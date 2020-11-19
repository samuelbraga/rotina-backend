package com.samuelbraga.rotinabackend.config.exception.dto;

import lombok.Data;

@Data
public class HttpMessageNotReadableExceptionDTO {
  private final String message;

  public HttpMessageNotReadableExceptionDTO(String message) {
    this.message = message;
  }
}
