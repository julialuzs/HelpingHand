package com.tcc.helpinghand.enums;

public enum Difficulty {

    BASIC("Básico"),
    INTERMEDIATE("Intermediário"),
    ADVANCED("Avançado");

    public final String label;

    private Difficulty(String label) {
        this.label = label;
    }
}
