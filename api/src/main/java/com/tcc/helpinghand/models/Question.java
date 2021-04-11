package com.tcc.helpinghand.models;

import javax.persistence.*;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idQuestion;

    private String sign;

    private String description;

    private String answer;

    // TODO: create enum?
    private String module;

    @ManyToOne
    @JoinColumn(name = "idLesson")
    private Lesson lesson;

}
