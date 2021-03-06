package com.tcc.helpinghand.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class Level implements Serializable {

    private long idLevel;

    private String description;

    private long maxPoints;

    private long minPoints;

}
