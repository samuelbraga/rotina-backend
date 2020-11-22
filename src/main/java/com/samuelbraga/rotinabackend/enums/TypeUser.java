package com.samuelbraga.rotinabackend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TypeUser {
  DEFAULT("DEFAULT"),
  ADMIN("ADMIN"),
  SUPER_ADMIN("SUPER_ADMIN");

  @Getter
  private final String value;
}
