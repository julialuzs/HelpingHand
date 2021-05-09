package com.tcc.helpinghand.services;

import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.models.requests.UserRequest;
import com.tcc.helpinghand.models.responses.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {

    @GET("user")
    Call<User> getCurrentUser(@Header("Authorization") String token);

    @POST("public/authentication/login")
    Call<LoginResponse> login(@Body UserRequest product);

    @POST("public/user")
    Call<User> signin(@Body User user);

    @PUT("user/{id}")
    Call<User> update(@Path("id") int id, @Body User user);

}
