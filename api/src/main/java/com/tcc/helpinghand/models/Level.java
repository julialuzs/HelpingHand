package com.tcc.helpinghand.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idLevel;

    @Column(length = 100, nullable=false)
    private String description;

    private long maxPoints;

    private long minPoints;

}
