package com.example.chatapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.chatapp.entities.Content;
import com.example.chatapp.repositories.ConversationRepo;

import java.util.List;

public class ConversationViewModel extends ViewModel {
    static ConversationRepo repo;
    private LiveData<List<Content>> conversationLiveData;

    public ConversationViewModel() {
        repo = new ConversationRepo();
        this.conversationLiveData = repo.getCov();
    }

    public LiveData<List<Content>> get() {
        return conversationLiveData;
    }


    public void receiveMessage(Content contact) {
        repo.receivedMess(contact);
    }

    public void addContent(Content contact) {
        repo.AddMessage(contact);
    }

    //set the id in the repo of the friend that the conversation is going to be
    public static void setFriend(String friend) {
        ConversationRepo.setFriendID(friend);
    }


}
