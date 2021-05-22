package com.tcc.helpinghand.controllers.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonResponse {

    private long idLesson;

    private String difficulty;

    private String module;

    private int rightAnswers;
}
