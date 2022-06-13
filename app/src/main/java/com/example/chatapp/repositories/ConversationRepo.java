package com.example.chatapp.repositories;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.chatapp.MyApplication;
import com.example.chatapp.entities.Contact;
import com.example.chatapp.entities.Content;
import com.example.chatapp.entities.Conversation;
import com.example.chatapp.entities.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ConversationRepo {

    static ContactDao contactDao;
    static ContentDao contentDao;
    private convListData convListData;
    static User loggedUser;
    private  MyApplication myApplication;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        ConversationRepo.token = token;
    }

    public ConversationRepo() {
        myApplication = new MyApplication();

        //create content(message) local db
        ContentDB contentDB = Room.databaseBuilder(myApplication.context,ContentDB.class,"ContentDB").allowMainThreadQueries().build();
        this.contentDao = contentDB.Dao();
        this.convListData = new convListData();

        //create contacts local db
        ContactDB contactDB = Room.databaseBuilder(myApplication.context,ContactDB.class,"ContactDB2").allowMainThreadQueries().build();
        this.contactDao = contactDB.Dao();
//        User nadav = new User( "nadav", "nadav Yakobivich","1234",contactDao.index());

        //loggedUser = nadav;
    }

    public  User getLoggedUser(){
        return loggedUser;
    }

//    public static void setLoggedUser(User loggedUser) {
//        ConversationRepo.loggedUser = loggedUser;
//    }

    public static void setLoggedUser(String id) {

        //get the user info from the local db
        List<Contact> contactList = contactDao.getUserContacts(id);
        loggedUser = new User(id,id,null,contactList);
        //AddData(); //to check add data to local


        //get the data from the server

    }

    public Conversation getConv(String fromUser, String toUser){
        List<Content> contentList = contentDao.getContents(fromUser,toUser);
        Conversation conversation = new Conversation(fromUser,toUser,contentList);
        return conversation;
    }

    public List<Contact> getContactList(){
        return loggedUser.getContacts();
    }

    public void AddContent(Content content){
        //insert to local db
        contentDao.insert(content);

        //update the last messages in the contact list
        Contact curr = null;
        List<Contact> contactList = loggedUser.getContacts();
        for (Contact contact: contactList){
            if (contact.getId().equals(content.getTo())){
                curr = contact;
                break;
            }
        }
        assert curr != null;
        curr.setLast(content.getContent());
        curr.setLastdate(content.getCreated());
        contactDao.update(curr);
        //update the static user object
        loggedUser.setContacts(contactDao.index());
    }

    public List<Content> getLsConv() {
        return convListData.getValue();
    }

    public void setLsConv(ArrayList<Content> lsConv) {
        this.convListData.setValue(lsConv); ;
    }

    public void addContact(Contact contact){
        this.contactDao.insert(contact);
        loggedUser.getContacts().add(contact);
    }

    public Contact getContact(String id){
        for( Contact contact : loggedUser.getContacts()){
            if (contact.getId().equals(id)){
                return contact;
            }
        }
        return null;
    }



    class convListData extends MutableLiveData<List<Content>> {

        public convListData() {
            super();
            setValue(new LinkedList<Content>());
        }

        @Override
        protected void onActive() {
            super.onActive();

            new Thread(() -> convListData.postValue(contentDao.index())).start();
        }
    }



    public static void AddData() {


        //create conversation from nadav to peleg
        List<Content> lstContent1 = new ArrayList<>();
        lstContent1.add(new Content("nadav","peleg", "hi", "17:50", true));
        lstContent1.add(new Content("nadav","peleg", "What up!", "17:51", false));
        lstContent1.add(new Content("nadav","peleg", "fine How are You?", "17:52", true));



        //create conversation from nadav to itamar
        List<Content> lstContent3 = new ArrayList<Content>();
        lstContent3.add(new Content("nadav","itamar", "hi its itamar", "17:50", false));
        lstContent3.add(new Content("nadav","itamar", "What up!", "17:51", true));
        lstContent3.add(new Content("nadav","itamar", "fine How are You?", "17:52", false));


        //contactDao.deleteAll();
        addContactToLocal();

        for (Content content: lstContent1){
            contentDao.insert(content);
        }

        for (Content content: lstContent3){
            contentDao.insert(content);
        }

    }

    //for check add hard data for the local db
    public static void addContactToLocal(){
        //create nadav User
        Contact pelegConNadav = new Contact("peleg","peleg","5555","hi","16:55",loggedUser.getId());
        Contact itamarConNadav = new Contact("itamar","itamar","5555","hi its itamar","16:55",loggedUser.getId());

        contactDao.insert(pelegConNadav);
        contactDao.insert(itamarConNadav);
    }


}
