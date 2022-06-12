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

    private ContactDao contactDao;
    private ContentDao contentDao;
    private convListData convListData;
    static User LoggedUser;
    private  MyApplication myApplication;

    static boolean isReset ;
//
//
//

//
    public Conversation getConv(String fromUser, String toUser){
        List<Content> contentList = contentDao.getContents(fromUser,toUser);
        Conversation conversation = new Conversation(fromUser,toUser,contentList);
        return conversation;
    }

    public List<Contact> getContactList(){
        return LoggedUser.getContacts();
    }

    public void AddContent(Content content){
        contentDao.insert(content);
        contactDao.getById(content.getTo());
    }

    public List<Content> getLsConv() {
        return convListData.getValue();
    }

    public void setLsConv(ArrayList<Content> lsConv) {
        this.convListData.setValue(lsConv); ;
    }

    public Contact getContact(String id){
        for( Contact contact : LoggedUser.getContacts()){
            if (contact.getId().equals(id)){
                return contact;
            }
        }
        return null;
    }

    public ConversationRepo() {
        myApplication = new MyApplication();

        //create content(message) local db
        ContentDB contentDB = Room.databaseBuilder(myApplication.context,ContentDB.class,"ContentDB").allowMainThreadQueries().build();
        this.contentDao = contentDB.Dao();
        this.convListData = new convListData();

        //create contacts local db
        ContactDB contactDB = Room.databaseBuilder(myApplication.context,ContactDB.class,"ContactDB1").allowMainThreadQueries().build();
        this.contactDao = contactDB.Dao();
        //AddData();
        User nadav = new User( "nadav", "nadav Yakobivich","1234",contactDao.index());

        LoggedUser= nadav;
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

    //for check add hard data for the local db
    public void addContactToLocal(){
        //create nadav User
        Contact pelegConNadav = new Contact("peleg","peleg","5555","hi","16:55");
        Contact itamarConNadav = new Contact("itamar","itamar","5555","hi its itamar","16:55");
        ArrayList<Contact> nadavContacts = new ArrayList<Contact>();
//        nadavContacts.add(pelegConNadav);
//        nadavContacts.add(itamarConNadav);

        contactDao.insert(pelegConNadav);
        contactDao.insert(itamarConNadav);
    }

    public void AddData() {


        //for check! only
//        if(isReset == true){
//            return;
//        }
//        isReset = true;
        //contentDao.deleteAll();

        List<Conversation> lsConv;
        lsConv = new ArrayList<Conversation>();
        //create conversation from nadav to peleg
        List<Content> lstContent1 = new ArrayList<>();
        lstContent1.add(new Content("nadav","peleg", "hi", "17:50", true));
        lstContent1.add(new Content("nadav","peleg", "What up!", "17:51", false));
        lstContent1.add(new Content("nadav","peleg", "fine How are You?", "17:52", true));
        Conversation nadavToPeleg = new Conversation("nadav", "peleg",lstContent1);



        //create conversation from nadav to itamar
        List<Content> lstContent3 = new ArrayList<Content>();
        lstContent3.add(new Content("nadav","itamar", "hi its itamar", "17:50", false));
        lstContent3.add(new Content("nadav","itamar", "What up!", "17:51", true));
        lstContent3.add(new Content("nadav","itamar", "fine How are You?", "17:52", false));
        Conversation nadavToItamar = new Conversation("nadav", "itamar",lstContent3);


        //contactDao.deleteAll();
        addContactToLocal();




        for (Content content: lstContent1){
            contentDao.insert(content);
        }

        for (Content content: lstContent3){
            contentDao.insert(content);
        }

    }


}
