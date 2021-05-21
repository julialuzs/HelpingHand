package com.tcc.helpinghand.controllers.response;

import lombok.Data;

@Data
public class QuestionResponse {

    boolean answerWasCorrect;
    long pointsGained;

//    boolean leveledUp;
//    Level newLevel;
}
