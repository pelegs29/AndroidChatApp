package com.example.chatapp.api;

import com.example.chatapp.ChatApp;
import com.example.chatapp.R;
import com.example.chatapp.entities.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsersAPI {

    Retrofit rerofit;
    UsersServiceAPI usersServiceAPI;

    public UsersAPI() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        rerofit = new Retrofit.Builder()
                .baseUrl(ChatApp.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        usersServiceAPI = rerofit.create(UsersServiceAPI.class);
    }

    public void login(User user) {
        Call<String> call = usersServiceAPI.authUser(user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                 String token = response.body();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }


}
