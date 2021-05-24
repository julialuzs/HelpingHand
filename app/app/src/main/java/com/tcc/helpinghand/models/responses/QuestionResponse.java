package com.tcc.helpinghand.models.responses;

import com.tcc.helpinghand.models.Level;

import java.io.Serializable;

import lombok.Data;

@Data
public class QuestionResponse implements Serializable {

    boolean answerCorrect;
    long pointsGained;
    boolean leveledUp;
    Level newLevel;

}
