package com.example.chatapp.repositories;

import android.icu.util.LocaleData;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.chatapp.entities.Contact;
import com.example.chatapp.entities.Content;
import com.example.chatapp.entities.Conversation;
import com.example.chatapp.entities.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ConversationRepo {

    private List<Conversation> lsConv;
    private User LoggedUser;



    public ConversationRepo() {
        this.lsConv = new ArrayList<Conversation>();
        //create conversation from nadav to peleg
        List<Content> lstContent1 = new ArrayList<Content>();
        lstContent1.add(new Content(1, "hi", "17:50", true));
        lstContent1.add(new Content(2, "What up!", "17:51", false));
        lstContent1.add(new Content(3, "fine How are You?", "17:52", true));
        Conversation nadavToPeleg = new Conversation("nadav", "peleg",lstContent1);

        //create conversation from peleg to nadav
        List<Content> lstContent2 = new ArrayList<Content>();
        lstContent2.add(new Content(1, "hi", "17:50", false));
        lstContent2.add(new Content(2, "What up!", "17:51", true));
        lstContent2.add(new Content(3, "fine How are You?", "17:52", false));
        Conversation pelegToNadav = new Conversation("peleg", "nadav",lstContent2);

        this.lsConv.add(nadavToPeleg);
        this.lsConv.add(pelegToNadav);

        //create nadav User
        Contact pelegConNadav = new Contact("peleg","peleg","5555","hi","16:55");
        Contact itamarConNadav = new Contact("itamar","itamar","5555","hi its itamar","16:55");
        ArrayList<Contact> nadavContacts = new ArrayList<Contact>();
        nadavContacts.add(pelegConNadav);
        nadavContacts.add(itamarConNadav);

        User nadav = new User( "nadav", "nadav Yakobivich","1234",nadavContacts);

        this.LoggedUser= nadav;
    }

    public Conversation getConv(String formUser, String toUSer){
        for(Conversation conv : lsConv){
            if (conv.getFrom().equals(formUser) && conv.getTo().equals(toUSer)){
                return conv;
            }
        }
        return null;
    }


    public List<Conversation> getLsConv() {
        return lsConv;
    }

    public void setLsConv(ArrayList<Conversation> lsConv) {
        this.lsConv = lsConv;
    }

    public Contact getContact(String id){
        for( Contact contact : LoggedUser.getContacts()){
            if (contact.getId().equals(id)){
                return contact;
            }
        }
        return null;
    }
    //    private ConversationDao dao;
//    private ConvListData convListData;
//
//
//    public ConversationRepo() {
//        ConversationDB db = ConversationDB.getInstance();
//        this.dao = db.Dao();
//    }
//
//
//    class ConvListData extends MutableLiveData<List<Conversation>> {
//
//        public ConvListData() {
//            super();
//            setValue(new LinkedList<Conversation>());
//        }
//
//        @Override
//        protected void onActive() {
//            super.onActive();
//
//            new Thread(() -> {
//                convListData.postValue(dao.get(););
//
//            }).start();
//        }
//    }
//
//    public LiveData<List<Conversation>> getAll() {
//        return ConvListData;
//    }
//
//    public MutableLiveData<Conversation> getByFriendName(String name) {
//        MutableLiveData<Conversation> conv = new MutableLiveData<Conversation>();
//        conv.setValue(dao.getByFirendName(name));
//        return conv;
//    }
}
