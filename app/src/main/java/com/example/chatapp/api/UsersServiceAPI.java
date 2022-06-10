package com.example.chatapp.api;

import com.example.chatapp.entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UsersServiceAPI {

    @POST("users")
    Call<String> authUser(@Body User user);

    @POST("users/new")
    Call<String> createUser(@Body User user);

    @GET("users")
    Call<List<User>> getAllUsers();

    @GET("users/{id}")
    Call<User> getUser(@Path("id") int id);

    @GET("users/find/{id}")
    Call<String> getIdByUsername(@Path("id") String id);


}
