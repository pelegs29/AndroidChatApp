package com.example.chatapp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chatapp.entities.Contact;

import java.util.List;

public class ContactsViewModel extends ViewModel {

    private MutableLiveData<List<Contact>> ls;

    public MutableLiveData<List<Contact>> getCon() {
        if (ls == null) {
            ls = new MutableLiveData<List<Contact>>();
        }
        return ls;
    }

//    public void addContent(String text){
//        String time = DateFormat.getDateTimeInstance().format(new Date());
//        Content newCon = new Content(1,text,time,true);
//        getCon().getValue().add(newCon);
//        ls.setValue(ls.getValue());
//
//    }
}
