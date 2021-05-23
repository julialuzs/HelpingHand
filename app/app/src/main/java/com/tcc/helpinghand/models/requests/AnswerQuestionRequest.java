package com.tcc.helpinghand.models.requests;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnswerQuestionRequest implements Serializable {
    String answer;
}
