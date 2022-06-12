package com.example.chatapp.api;

import com.example.chatapp.ChatApp;
import com.example.chatapp.LoginActivity;
import com.example.chatapp.R;
import com.example.chatapp.SignupActivity;
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

    public void login(User user, LoginActivity activity) {
        Call<String> TokenCall = webServiceAPI.authUser(user);
        TokenCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ConversationRepo.setToken("Bearer " + response.body());
                if (response.code() == 400) {
                    try {
                        if (response.errorBody() != null &&
                                response.errorBody().string().equals("Invalid credentials")) {
                            activity.showAlert(-2);
                        } else {
                            activity.showAlert(-1);
                        }
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
                            activity.showAlert(-1);
                            return;
                        }
                        ConversationRepo.setLoggedUser(response.body());
                        activity.goToMain();
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

    public void findUsername(String username, SignupActivity activity) {
        Call<String> findCall = webServiceAPI.getIdByUsername(username);
        findCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {
                    activity.runOnUiThread(activity::usernameTaken);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });

    }

    public void addUser(User user, SignupActivity activity) {
        Call<String> call = webServiceAPI.createUser(user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ConversationRepo.setToken("Bearer " + response.body());
                if (response.code() == 400) {
                    activity.showAlert();
                    return;
                }

                Call<User> userCall = webServiceAPI.getUser(user.getId(), ConversationRepo.getToken());
                userCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.code() == 400) {
                            activity.showAlert();
                            return;
                        }
                        ConversationRepo.setLoggedUser(response.body());
                        activity.goToMain();
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

}
