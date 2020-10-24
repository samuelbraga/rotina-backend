package com.samuelbraga.rotinabackend.Modules.User.Enums;

import java.util.Arrays;

public enum TypeUser {
  DEFAULT("DEFAULT"),
  ADMIN("ADMIN"),
  SUPER_ADMIN("SUPER_ADMIN");

  private final String value;

  TypeUser(String value) {
    this.value = value;
  }

  public static TypeUser fromValue(String value) {
    for (TypeUser category : values()) {
      if (category.value.equalsIgnoreCase(value)) {
        return category;
      }
    }
    
    throw new IllegalArgumentException(
      "Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
  }

}
