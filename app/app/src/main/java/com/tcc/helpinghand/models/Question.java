package com.tcc.helpinghand.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class Question implements Serializable {

    private long idQuestion;

    private String sign;

    private String description;

    private String correctAnswer;

    private String answerOptions;

    private Lesson lesson;

}
