package com.example.chatapp.api;

import com.example.chatapp.ChatApp;
import com.example.chatapp.R;
import com.example.chatapp.entities.FirebaseUser;
import com.example.chatapp.entities.Invitation;
import com.example.chatapp.entities.Transfer;
import com.example.chatapp.repositories.ConversationRepo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CrossServerAPI {

    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public CrossServerAPI() {
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

    public CrossServerAPI(String server) {
        String serverPort = parseServerPort(server);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:" + serverPort + "/api/")
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    private String parseServerPort(String server) {
        int size = server.length();
        if (server.endsWith("/")) {
            return server.substring(size - 5, size - 1);
        }
        return server.substring(size - 4);
    }

    public void register(FirebaseUser user) {
        Call<Void> call = webServiceAPI.register(user, ConversationRepo.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

    public void transfer(Transfer transfer) {
        Call<Void> call = webServiceAPI.transfer(transfer, ConversationRepo.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

    public void invitation(Invitation invitation) {
        Call<Void> call = webServiceAPI.invitation(invitation, ConversationRepo.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

}
