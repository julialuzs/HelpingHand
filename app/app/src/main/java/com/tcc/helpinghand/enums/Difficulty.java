package com.tcc.helpinghand.enums;

public enum Difficulty {

    BASIC("BÁSICAS"),
    INTERMEDIATE("INTERMEDIÁRIAS"),
    ADVANCED("AVANÇADAS");

    public final String label;

    private Difficulty(String label) {
        this.label = label;
    }
}
