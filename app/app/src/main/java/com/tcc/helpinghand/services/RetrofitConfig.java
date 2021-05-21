package com.tcc.helpinghand.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    private static final String URL = "http://192.168.1.103:8080/";

    Retrofit retrofit;

    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public UserService getUserService() {
        return this.retrofit.create(UserService.class);
    }

    public LessonService getLessonService() {
        return this.retrofit.create(LessonService.class);
    }
}
