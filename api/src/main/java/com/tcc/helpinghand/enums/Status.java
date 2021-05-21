package com.tcc.helpinghand.enums;

import java.util.Arrays;

public enum Status {
    BLOCKED("Bloqueado"),
    INPROGRESS("Em progresso"),
    CONCLUDED("Concluído");

    public final String label;

    private Status(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Status getByName(String label) {
        return Arrays.stream(Status.values())
                .filter(status -> status.getLabel() == label)
                .findFirst()
                .orElse(BLOCKED);
    }
}

