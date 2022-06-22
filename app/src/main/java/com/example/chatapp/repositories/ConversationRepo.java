package com.example.chatapp.repositories;

import android.graphics.Bitmap;

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
import com.example.chatapp.entities.ProfilePic;
import com.example.chatapp.entities.Transfer;
import com.example.chatapp.entities.User;

import java.util.List;

public class ConversationRepo {

    private ContactDao contactDao;
    private ContentDao contentDao;
    static ProfilePicDao profilePicDao;

    static User loggedUser;
    static String token;
    private ConvData convData;
    static ContactsData contactsData;
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

        //create contacts local profile pic db
        ProfilePicDB profileDB = Room.databaseBuilder(ChatApp.context, ProfilePicDB.class, "ProfileDB").allowMainThreadQueries().build();
        this.profilePicDao = profileDB.Dao();

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


    public LiveData<List<Contact>> getContactList() {
        return contactsData;
    }

    public void AddMessage(Content content) {
        //insert to local db
        contentDao.insert(content);
        convData.getValue().add(content);
        convData.setValue(convData.getValue());

        //insert to api server
        messagesAPI.postMessage(content.getTo(), content);

        //update the last messages in the contact list
        updateContact(content);
        Contact curr = getContact(content.getTo());

        //update the static user object
        loggedUser.setContacts(contactDao.index());
        CrossServerAPI crossServerAPI = new CrossServerAPI(curr.getServer());
        //call transfer on friend server
        Transfer transfer = new Transfer(content.getFrom(), content.getTo(), content.getContent());
        crossServerAPI.transfer(transfer);
    }

    public void receivedMess(Content content) {
        //insert to local db
        contentDao.insert(content);
        convData.getValue().add(content);

        //set the value  -> make the adapter restart
        convData.postValue(convData.getValue());
        updateContact(content);
    }

    //update the last messege and time of the last mess in the contact
    public void updateContact(Content content) {
        Contact curr = getContact(content.getTo());
        curr.setLast(content.getContent());
        curr.setLastdate(content.getCreated());
        contactDao.update(curr);
    }

    public void updateContactList(Content content) {
        updateContact(content);
//        Collections.sort(contactsData.getValue(), Collections.reverseOrder());
        contactsData.postValue(contactsData.getValue());
    }


    //add all the contact form the server to the local data
    public void setUpContacts() {
        this.contactDao.deleteAll();
        for (Contact contact : loggedUser.getContacts()) {
            contact.setContactOf(loggedUser.getId());
            contactDao.insert(contact);
        }
        contactsData.setValue(loggedUser.getContacts());
    }

    public void addContact(Contact contact) {

        if (getContact(contact.getId()) != null) {
            //the contact is already in my contacts
            return;
        }
        this.contactDao.insert(contact);
        loggedUser.getContacts().add(contact);
        contactsData.getValue().add(contact);

        //add to the server
//        ContactsAPI contactsAPI = new ContactsAPI();
//        contactsAPI.postContact(contact);

        contactsData.setValue(contactsData.getValue());

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
                contact.getId(), ChatApp.context.getString(R.string.localHost), null);
        CrossServerAPI crossServerAPI1 = new CrossServerAPI(contact.getServer());
        crossServerAPI1.invitation(inviteFriend);
    }

    public Contact getContact(String id) {
        for (Contact contact : contactsData.getValue()) {
            if (contact.getId().equals(id)) {
                return contact;
            }
        }
        return null;
    }

    public void updateContactsFromServer(Contact contact) {
        contactsData.getFromServer(contact);
    }


    class ConvData extends MutableLiveData<List<Content>> {

        public ConvData() {
            super();
            if (friendID != null) {
                List<Content> contentList = contentDao.getContents(loggedUser.getId(), friendID);
                setValue(contentList);

                messagesAPI.getConversation(friendID, this);
            }
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

        public void getFromServer(Contact contact) {
            contactDao.insert(contact);
            getValue().add(contact);
            postValue(getValue());
        }

        @Override
        protected void onActive() {
            super.onActive();

//            new Thread(() -> contactsData.postValue(contactDao.getUserContacts(loggedUser.getId()))).start();
        }
    }

    public static void uploadProfilePic(String id, Bitmap pic) {
        ProfilePicDB profileDB = Room.databaseBuilder(ChatApp.context, ProfilePicDB.class, "ProfileDB").allowMainThreadQueries().build();
        ProfilePicDao profilePicDao = profileDB.Dao();
        profilePicDao.insert(new ProfilePic(id, pic));
    }

    public static Bitmap GetBitmap(String id) {

        ProfilePic bitmap = profilePicDao.get(id);
        if (bitmap == null) {
            return null;
        } else {
            return bitmap.getImageBitMap();
        }
    }

}
