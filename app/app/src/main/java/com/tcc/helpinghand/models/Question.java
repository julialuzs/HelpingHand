package com.tcc.helpinghand.models;

import lombok.Data;

@Data
public class Question {

    private long idQuestion;

    private String sign;

    private String description;

    private String correctAnswer;

    private String answerOptions;

    private Lesson lesson;

}
