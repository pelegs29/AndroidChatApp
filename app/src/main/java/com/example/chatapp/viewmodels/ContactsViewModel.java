package com.example.chatapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.chatapp.entities.Contact;
import com.example.chatapp.entities.Content;
import com.example.chatapp.entities.User;
import com.example.chatapp.repositories.ConversationRepo;

import java.util.List;

public class ContactsViewModel extends ViewModel {

    static ConversationRepo repo;
    private LiveData<List<Contact>> contactLiveData;

    public ContactsViewModel() {
        repo = new ConversationRepo();
        this.contactLiveData = repo.getContactList();
    }

    public LiveData<List<Contact>> get() {
        //sent request to update the data from the server
        //repo.updateContclsFromServer();
        return repo.getContactList();
    }

    public void setUpContacts() {
        repo.setUpContacts();
    }

    public Contact getContactById(String id) {
        return repo.getContact(id);
    }

    public void updateContact(Content content) {
        repo.updateContactList(content);
    }

    public User getUserLogged() {
        return ConversationRepo.getLoggedUser();
    }

    public void updateFromServer(Contact contact) {
        repo.updateContactsFromServer(contact);
    }


//    public void addContent(String text){
//        String time = DateFormat.getDateTimeInstance().format(new Date());
//        Content newCon = new Content(1,text,time,true);
//        getCon().getValue().add(newCon);
//        ls.setValue(ls.getValue());
//
//    }
}
