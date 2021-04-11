package com.tcc.helpinghand.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;

@Data
@Entity
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idLevel;

    private String description;

    private long maxPoints;

    private long minPoints;

//    private ArrayList<User> users;

}
