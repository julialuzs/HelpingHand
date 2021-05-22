package com.tcc.helpinghand.models;

import com.tcc.helpinghand.enums.Difficulty;
import com.tcc.helpinghand.enums.Status;

import java.io.Serializable;

import lombok.Data;

@Data
public class Lesson implements Serializable {

    private long idLesson;

    private long points;

    private Status status;

    private Difficulty difficulty;

    private String module;

    private String imageName;

}
