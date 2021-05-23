package com.tcc.helpinghand.models.responses;

import java.io.Serializable;

import lombok.Data;

@Data
public class QuestionResponse implements Serializable {

    boolean answerCorrect;

    long pointsGained;

}
