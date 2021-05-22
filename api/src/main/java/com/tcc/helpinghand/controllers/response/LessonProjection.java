package com.tcc.helpinghand.controllers.response;

public interface LessonProjection {
    long getIdLesson();

    String getDifficulty();

    String getModule();

    int getRightAnswers();

    int getImageId();
}
