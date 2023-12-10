package com.devsling.constants;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransmissionType {

    @JsonProperty("Manual")
    MANUAL("Manual"),

    @JsonProperty("Semi Automatic")
    SEMI_AUTOMATIC("Semi Automatic"),

    @JsonProperty("Automatic")
    AUTOMATIC("Automatic");

    private final String label;

    public static TransmissionType of(String label) {
        return Stream.of(values())
                .filter(e -> e.getLabel().equalsIgnoreCase(label))
                .findFirst()
                .orElse(null);
    }
}