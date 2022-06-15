package com.example.chatapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.chatapp.ChatApp;
import com.example.chatapp.R;
import com.example.chatapp.api.ContactsAPI;
import com.example.chatapp.api.CrossServerAPI;
import com.example.chatapp.api.MessagesAPI;
import com.example.chatapp.entities.Contact;
import com.example.chatapp.entities.Content;
import com.example.chatapp.entities.Invitation;
import com.example.chatapp.entities.Transfer;
import com.example.chatapp.entities.User;

import java.util.ArrayList;
import java.util.List;

public class ConversationRepo {

    private ContactDao contactDao;
    private ContentDao contentDao;
    static User loggedUser;
    static String token;
    private ConvData convData;
    private ContactsData contactsData;
    static String friendID;
    private MessagesAPI messagesAPI;


    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        ConversationRepo.token = token;
    }

    public ConversationRepo() {
        this.messagesAPI = new MessagesAPI();

        //create content(message) local db
        ContentDB contentDB = Room.databaseBuilder(ChatApp.context, ContentDB.class, "ContentDB").allowMainThreadQueries().build();
        this.contentDao = contentDB.Dao();

        //create contacts local db
        ContactDB contactDB = Room.databaseBuilder(ChatApp.context, ContactDB.class, "ContactDB2").allowMainThreadQueries().build();
        this.contactDao = contactDB.Dao();

        convData = new ConvData();
        contactsData = new ContactsData();

    }


    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User loggedUser) {
        ConversationRepo.loggedUser = loggedUser;
    }

    public static String getFriendID() {
        return friendID;
    }

    public static void setFriendID(String friendID) {
        ConversationRepo.friendID = friendID;
    }


    public LiveData<List<Content>> getCov() {
        return convData;
    }

//    public Conversation getConv( String toUser) {
//        List<Content> contentList = contentDao.getContents(loggedUser.getId(), toUser);
//        Conversation conversation = new Conversation(loggedUser.getId(), toUser, contentList);
//        return conversation;
//    }

    public LiveData<List<Contact>> getContactList() {
        return contactsData;
    }

    public void AddContent(Content content) {
        //insert to local db
        contentDao.insert(content);
        convData.getValue().add(content);
        convData.setValue(convData.getValue());

        //insert to api server
        messagesAPI.postMessage(content.getTo(), content);

        //update the last messages in the contact list
        Contact curr = null;
        List<Contact> contactList = loggedUser.getContacts();
        for (Contact contact : contactList) {
            if (contact.getId().equals(content.getTo())) {
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


        CrossServerAPI crossServerAPI = new CrossServerAPI(curr.getServer());
        //call transfer on friend server
        Transfer transfer = new Transfer(content.getFrom(), content.getTo(), content.getContent());
        crossServerAPI.transfer(transfer);
    }

    public void setUpContacts() {
        this.contactDao.deleteAll();


        for (Contact contact : loggedUser.getContacts()) {
            contact.setContactOf(loggedUser.getId());
            contactDao.insert(contact);
        }
        contactsData.setValue(loggedUser.getContacts());
    }

    public void addContact(Contact contact) {
        this.contactDao.insert(contact);
        loggedUser.getContacts().add(contact);

        //add to the server
        ContactsAPI contactsAPI = new ContactsAPI();
        contactsAPI.postContact(contact);

        contactsData.setValue(loggedUser.getContacts());

        //send invitations to my server & friend's server
        sendInvitations(contact);
    }

    private void sendInvitations(Contact contact) {
        //sending invitation to my server from friend
        Invitation friendInviteMe = new Invitation(contact.getId(),
                getLoggedUser().getId(), contact.getServer(), contact.getName());
        CrossServerAPI crossServerAPI = new CrossServerAPI();
        crossServerAPI.invitation(friendInviteMe);

        //sending invitation to friend's server from me
        Invitation inviteFriend = new Invitation(loggedUser.getId(),
                contact.getId(), ChatApp.context.getString(R.string.BaseUrl), null);
        CrossServerAPI crossServerAPI1 = new CrossServerAPI(contact.getServer());
        crossServerAPI1.invitation(inviteFriend);
    }

    public Contact getContact(String id) {
        for (Contact contact : loggedUser.getContacts()) {
            if (contact.getId().equals(id)) {
                return contact;
            }
        }
        return null;
    }


    class ConvData extends MutableLiveData<List<Content>> {

        public ConvData() {
            super();
            if (friendID != null) {
                List<Content> contentList = contentDao.getContents(loggedUser.getId(), friendID);
                setValue(contentList);

                messagesAPI.getConversation(friendID, this);
            }
            //usersAPI.
        }

        @Override
        protected void onActive() {
            super.onActive();

//            new Thread(() -> {
//                List<Content> contentList = contentDao.getContents(loggedUser.getId(), friendID);
//                convData.postValue(contentList);
//            }).start();
        }
    }

    class ContactsData extends MutableLiveData<List<Contact>> {

        public ContactsData() {
            super();
            List<Contact> contactList = contactDao.getUserContacts(loggedUser.getId());
            setValue(contactList);

            ContactsAPI contactsAPI = new ContactsAPI();
            contactsAPI.getContacts(this);
        }

        @Override
        protected void onActive() {
            super.onActive();

//            new Thread(() -> contactsData.postValue(contactDao.getUserContacts(loggedUser.getId()))).start();
        }
    }


    public void AddData() {


        //create conversation from nadav to pelegs29
        List<Content> lstContent1 = new ArrayList<>();
        lstContent1.add(new Content("nadavyk", "pelegs29", "hi", "17:50", true));
        lstContent1.add(new Content("nadavyk", "pelegs29", "What up!", "17:51", false));
        lstContent1.add(new Content("nadavyk", "pelegs29", "fine How are You?", "17:52", true));


        //create conversation from nadavyk to itamarb
        List<Content> lstContent3 = new ArrayList<>();
        lstContent3.add(new Content("nadavyk", "itamarb", "hi its itamarb", "17:50", false));
        lstContent3.add(new Content("nadavyk", "itamarb", "What up!", "17:51", true));
        lstContent3.add(new Content("nadavyk", "itamarb", "fine How are You?", "17:52", false));


        //contactDao.deleteAll();
        addContactToLocal();

        for (Content content : lstContent1) {
            contentDao.insert(content);
        }

        for (Content content : lstContent3) {
            contentDao.insert(content);
        }

    }

    //for check add hard data for the local db
    public void addContactToLocal() {
        //create nadavyk User
        Contact pelegConNadav = new Contact("pelegs29", "pelegs29", "5555", "hi", "16:55", loggedUser.getId());
        Contact itamarConNadav = new Contact("itamarb", "itamarb", "5555", "hi its itamarb", "16:55", loggedUser.getId());

        contactDao.insert(pelegConNadav);
        contactDao.insert(itamarConNadav);
    }


}
