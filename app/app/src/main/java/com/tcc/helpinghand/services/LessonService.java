package com.tcc.helpinghand.services;

import com.tcc.helpinghand.models.Lesson;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LessonService {

    @GET("lesson")
    Call<List<Lesson>> getAll();
}
