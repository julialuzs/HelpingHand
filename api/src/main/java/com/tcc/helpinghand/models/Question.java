package com.tcc.helpinghand.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idQuestion;

    @Column(nullable = false)
    private String sign;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String correctAnswer;

    @Column(nullable = false)
    private String answerOptions;

    @ManyToOne
    @JoinColumn(name = "idLesson")
    private Lesson lesson;

}
