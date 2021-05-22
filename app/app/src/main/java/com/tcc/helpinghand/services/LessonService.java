package com.tcc.helpinghand.services;

import com.tcc.helpinghand.models.Lesson;
import com.tcc.helpinghand.models.Question;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface LessonService {

    @GET("lesson")
    Call<List<Lesson>> getAll();

    @GET("lesson/current-user")
    Call<List<Lesson>> getAllByCurrentUser(@Header("Authorization") String token);

    @GET("lesson/{lessonId}/questions")
    Call<List<Question>> getQuestionsByLesson(@Path("lessonId") int lessonId);
}
