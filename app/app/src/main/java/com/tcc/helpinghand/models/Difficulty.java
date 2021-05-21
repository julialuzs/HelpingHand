package com.tcc.helpinghand.models;

public enum Difficulty {

    BASIC("Básico"),
    INTERMEDIATE("Intermediário"),
    ADVANCED("Avançado");

    public final String label;

    private Difficulty(String label) {
        this.label = label;
    }
}
