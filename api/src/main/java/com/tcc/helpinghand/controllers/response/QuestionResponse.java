package com.tcc.helpinghand.controllers.response;

import com.tcc.helpinghand.models.Level;
import lombok.Data;

@Data
public class QuestionResponse {

    boolean answerCorrect;
    long pointsGained;
    boolean leveledUp;
    Level newLevel;

}
