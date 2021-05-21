package com.tcc.helpinghand.models;


public enum Status {
    BLOCKED("Bloqueado"),
    INPROGRESS("Em progresso"),
    CONCLUDED("Concluído");

    public final String label;

    private Status(String label) {
        this.label = label;
    }
}
