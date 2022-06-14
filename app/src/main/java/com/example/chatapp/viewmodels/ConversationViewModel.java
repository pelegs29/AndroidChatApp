package com.example.chatapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chatapp.entities.Contact;
import com.example.chatapp.entities.Content;
import com.example.chatapp.entities.Conversation;
import com.example.chatapp.entities.User;
import com.example.chatapp.repositories.ContentDao;
import com.example.chatapp.repositories.ConversationRepo;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class ConversationViewModel extends ViewModel {
    ConversationRepo repo;
    private LiveData<List<Content>> conversationLiveData;

    public ConversationViewModel() {
        this.repo = new ConversationRepo();
        this.conversationLiveData = repo.getCov();
    }

    public  LiveData<List<Content>> get(){
        return conversationLiveData;
    }

    public void addContent(Content contact){
        repo.AddContent(contact);
    }

    //set the id in the repo of the friend that the conversation is going to be
    public static void  setFriend(String friend){
        ConversationRepo.setFriendID(friend);
    }



}
