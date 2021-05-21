package com.tcc.helpinghand.models;

import lombok.Data;

@Data
public class Lesson {

    private long idLesson;

    private long points;

    private Status status;

    private Difficulty difficulty;

    private String module;
}
