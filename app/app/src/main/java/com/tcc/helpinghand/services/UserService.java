package com.tcc.helpinghand.services;

import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.models.requests.UserRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {

    @POST("public/authentication/login")
    Call<User> login(@Body UserRequest product);

    @POST("public/user")
    Call<User> signin(@Body User user);

    @PUT("user/{id}")
    Call<User> update(@Path("id") int id, @Body User user);

}
