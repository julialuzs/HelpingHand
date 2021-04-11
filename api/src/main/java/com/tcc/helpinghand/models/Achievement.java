package com.tcc.helpinghand.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.File;

@Data
@Entity
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAchievement;

    private String name;

    private String description;

    private File image;
}
