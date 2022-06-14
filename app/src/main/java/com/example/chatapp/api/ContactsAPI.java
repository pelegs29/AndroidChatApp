package com.example.chatapp.api;

import androidx.lifecycle.MutableLiveData;

import com.example.chatapp.ChatApp;
import com.example.chatapp.R;
import com.example.chatapp.entities.Contact;
import com.example.chatapp.repositories.ConversationRepo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactsAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public ContactsAPI() {
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


    public void getContacts(MutableLiveData<List<Contact>> ContactsData) {
        Call<List<Contact>> call = webServiceAPI.getContacts(ConversationRepo.getToken());
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                if (response.code() == 200) {
                    ContactsData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
            }
        });
    }

    public void postContact(Contact contact) {
        Call<ResponseBody> call = webServiceAPI.postContact(contact, ConversationRepo.getToken());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
