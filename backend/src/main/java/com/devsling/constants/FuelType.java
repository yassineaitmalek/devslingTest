package com.devsling.constants;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FuelType {

  @JsonProperty("Disel")
  DIESEL("Disel"),

  @JsonProperty("Electric")
  ELECTRIC("Electric"),

  @JsonProperty("Hybrid")
  HYBRID("Hybrid");

  private final String label;

  public static FuelType of(String label) {
    return Stream.of(values())
        .filter(e -> e.getLabel().equalsIgnoreCase(label))
        .findFirst()
        .orElse(null);
  }
}
