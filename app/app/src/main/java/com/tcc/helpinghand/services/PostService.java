package com.tcc.helpinghand.services;

import com.tcc.helpinghand.models.Post;
import com.tcc.helpinghand.models.Comment;
import com.tcc.helpinghand.models.requests.PostRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PostService {

    @GET("post")
    Call<List<Post>> getPosts(@Query("tag") String tag);

    @POST("post")
    Call<Post> post(@Header("Authorization") String token, @Body PostRequest post);

    @POST("post/{postId}/comment")
    Call<Comment> comment(
            @Header("Authorization") String token,
            @Body Comment comment,
            @Path("postId") int postId
    );

    @POST("post/{postId}/like")
    Call<Void> like(
            @Header("Authorization") String token,
            @Path("postId") int postId
    );

}
