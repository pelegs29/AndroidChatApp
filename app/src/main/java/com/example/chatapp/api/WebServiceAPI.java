package com.example.chatapp.api;

import com.example.chatapp.entities.Contact;
import com.example.chatapp.entities.Content;
import com.example.chatapp.entities.FirebaseUser;
import com.example.chatapp.entities.Invitation;
import com.example.chatapp.entities.Transfer;
import com.example.chatapp.entities.User;

import java.util.List;

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
    Call<User> getUser(@Path("id") String id, @Header("Authorization") String BearerToken);

    @GET("users/find/{id}")
    Call<String> getIdByUsername(@Path("id") String id);

    //    ______________MessagesController Requests______________

    @GET("contacts/{friendId}/messages")
    Call<List<Content>> getConversationWith(@Path("friendId") String friendId,
                                            @Header("Authorization") String BearerToken);

    @POST("contacts/{friendId}/messages")
    Call<Void> postMessage(@Path("friendId") String friendId,
                                   @Body Content content,
                                   @Header("Authorization") String BearerToken);

    //    ______________ContactsController Requests______________

    @GET("contacts")
    Call<List<Contact>> getContacts(@Header("Authorization") String BearerToken);

    @POST("contacts")
    Call<Void> postContact(@Body Contact contact,
                                   @Header("Authorization") String BearerToken);

    //    ______________CrossServerController Requests______________

    @POST("firebase/register")
    Call<Void> register(@Body FirebaseUser user, @Header("Authorization") String BearerToken);

    @POST("transfer")
    Call<Void> transfer(@Body Transfer transfer, @Header("Authorization") String BearerToken);

    @POST("invitations")
    Call<Void> invitation(@Body Invitation invitation, @Header("Authorization") String BearerToken);

}
