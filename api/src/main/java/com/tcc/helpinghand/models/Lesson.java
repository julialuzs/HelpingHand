package com.tcc.helpinghand.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;

@Data
@Entity
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idLesson;

    private long points;

    // TODO: create enum? (Blocked/ In progress/ Concluded)
    private String status;

    // TODO: create enum?
    private String difficulty;

    // TODO: create enum?
    private String module;

//    private ArrayList<Question> questions;
}
