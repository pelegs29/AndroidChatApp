package com.example.chatapp.api;

import androidx.lifecycle.MutableLiveData;

import com.example.chatapp.ChatApp;
import com.example.chatapp.R;
import com.example.chatapp.entities.Content;
import com.example.chatapp.repositories.ConversationRepo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessagesAPI {

    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public MessagesAPI() {
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

    public void getConversation(String friendId, MutableLiveData<List<Content>> ConvData) {
        Call<List<Content>> call = webServiceAPI.getConversationWith(friendId, ConversationRepo.getToken());
        call.enqueue(new Callback<List<Content>>() {
            @Override
            public void onResponse(Call<List<Content>> call, Response<List<Content>> response) {
                if (response.code() == 200) {
                    ConvData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Content>> call, Throwable t) {
            }
        });
    }

    public void postMessage(String friendId, Content content) {
        Call<Void> call = webServiceAPI.postMessage(friendId, content,
                ConversationRepo.getToken());
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
