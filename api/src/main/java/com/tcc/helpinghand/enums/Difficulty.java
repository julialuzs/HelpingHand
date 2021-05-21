package com.tcc.helpinghand.enums;

import java.util.Arrays;

public enum Difficulty {

    BASIC("Básico"),
    INTERMEDIATE("Intermediário"),
    ADVANCED("Avançado");

    public String label;

    Difficulty(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Difficulty getByName(String label) {
        return Arrays.stream(Difficulty.values())
                .filter(difficulty -> difficulty.getLabel().equals(label))
                .findFirst()
                .orElse(BASIC);
    }
}
