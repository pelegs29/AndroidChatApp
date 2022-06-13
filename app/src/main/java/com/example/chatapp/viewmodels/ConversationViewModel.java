package com.example.chatapp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chatapp.entities.Content;

import java.util.List;

public class ConversationViewModel extends ViewModel {

    private MutableLiveData<List<Content>> ls;
    
    public MutableLiveData<List<Content>> getCon() {
        if (ls == null) {
            ls = new MutableLiveData<List<Content>>();
        }
        return ls;
    }


    //the repo Its purpose is to manage all connections with the database, whether the server or with the local database
//    private ConversationRepo mRepository;
//    private MutableLiveData<Conversation> conversation;
//
//    public ConversationViewModel(String friendName) {
//        this.mRepository = new ConversationRepo();
//        //get the conversation with the friend
//        this.conversation = mRepository.getByFriendName(friendName);
//    }
//
//    public MutableLiveData<Conversation> get(){
//        return conversation;
//    }
//
//    add
//
//    delete
//
//    reload


}
