package com.example.chatapp.api;

import com.example.chatapp.entities.Contact;
import com.example.chatapp.entities.Content;
import com.example.chatapp.entities.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {

//    ______________UsersController Requests______________

    @POST("users")
    Call<String> authUser(@Body User user);

    @POST("users/new")
    Call<String> createUser(@Body User user);

    @GET("users")
    Call<List<User>> getAllUsers();

    @GET("users/{id}")
    Call<User> getUser(@Path("id") String id, @Header("Authorization") String token);

    @GET("users/find/{id}")
    Call<String> getIdByUsername(@Path("id") String id);

    //    ______________MessagesController Requests______________

    @GET("contacts/{friendId}/messages")
    Call<List<Content>> getConversationWith(@Path("friendId") String friendId,
                                            @Header("Authorization") String token);

    @POST("contacts/{friendId}/messages")
    Call<ResponseBody> postMessage(@Path("friendId") String friendId,
                                   @Body Content content,
                                   @Header("Authorization") String token);

    //    ______________ContactsController Requests______________

    @GET("contacts")
    Call<List<Contact>> getContacts(@Header("Authorization") String token);

    @POST("contacts")
    Call<ResponseBody> postContact(@Body Contact contact, @Header("Authorization") String token);

}
