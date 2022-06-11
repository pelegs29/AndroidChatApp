package com.example.chatapp.api;

import android.app.AlertDialog;
import android.content.Context;

import com.example.chatapp.ChatApp;
import com.example.chatapp.LoginActivity;
import com.example.chatapp.R;
import com.example.chatapp.entities.User;
import com.example.chatapp.repositories.ConversationRepo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsersAPI {

    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public UsersAPI() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(ChatApp.context.getString(R.string.BaseUrl))
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);

    }

    public void login(User user, Context context, AlertDialog.Builder alertBuilder) {
        LoginActivity loginActivity = (LoginActivity) context;

        Call<String> TokenCall = webServiceAPI.authUser(user);
        TokenCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ConversationRepo.setToken("Bearer " + response.body());
                if (response.code() == 400) {
                    try {
                        if (response.errorBody() != null &&
                                response.errorBody().string().equals("Invalid credentials")) {
                            alertBuilder.setMessage("You have entered wrong credentials," +
                                    " please try again");

                        } else {
                            alertBuilder.setMessage("Something went wrong, please try again");
                        }
                        loginActivity.runOnUiThread(alertBuilder::show);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }

                Call<User> userCall = webServiceAPI.getUser(user.getId(), ConversationRepo.getToken());
                userCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.code() == 400) {
                            alertBuilder.setMessage("Something went wrong, please try again");
                            alertBuilder.setPositiveButton("OK", (dialog, which) -> dialog.cancel());
                            loginActivity.runOnUiThread(alertBuilder::show);
                            return;
                        }
                        ConversationRepo.setLoggedUser(response.body());
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void findUsername(String username) {
        Call<String> findCall = webServiceAPI.getIdByUsername(username);
        findCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 404) {
                    //TODO: HANDLE USER NAME FIND
                } else {

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });

    }


}
