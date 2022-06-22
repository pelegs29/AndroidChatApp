package com.example.chatapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.adapters.ContactsAdapter;
import com.example.chatapp.api.CrossServerAPI;
import com.example.chatapp.entities.Contact;
import com.example.chatapp.entities.FirebaseUser;
import com.example.chatapp.repositories.ConversationRepo;
import com.example.chatapp.viewmodels.ContactsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Collections;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    private ContactsViewModel viewContacts;
    ContactsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        viewContacts = new ViewModelProvider(this).get(ContactsViewModel.class);

        viewContacts.setUpContacts();

        firebaseService.setContactsViewModel(viewContacts);

        //set the name of the user on the top of the page
        TextView userNameTag = findViewById(R.id.userNameConWin);
        userNameTag.setText(viewContacts.getUserLogged().getName());

        ImageView ProfilePic = findViewById(R.id.profilePicConWinMain);

        setProfilePic(ProfilePic,ConversationRepo.getLoggedUser().getId());

        //the convList - hold the content in the win
        RecyclerView contactList = findViewById(R.id.contact_rcContact);

        this.adapter = new ContactsAdapter(this);
        contactList.setAdapter(adapter); // connect the adapter to the RecyclerView
        contactList.setLayoutManager(new LinearLayoutManager(this)); // make the item in the RecyclerView appear in liner order
        List<Contact> sortListContacts = viewContacts.get().getValue();
        adapter.setLstContent(sortListContacts);

        // ls - contain the update Content List
        viewContacts.get().observe(this, ls -> {
            Collections.sort(ls, Collections.reverseOrder());
            adapter.setLstContent(ls);
        });

        // set the function when the user click on btn
        contactList.setClickable(true);
        FloatingActionButton addBtn = findViewById(R.id.contacts_add);
        addBtn.setOnClickListener(v -> {
            Intent i = new Intent(this, AddNewContactsActivity.class);
            startActivity(i);
        });

        FloatingActionButton settingBtn = findViewById(R.id.Setting_Page);
        settingBtn.setOnClickListener(v -> {
            Intent i = new Intent(this, SettingsPageActivity.class);
            startActivity(i);
        });


        //contact to fireBase
        //get the token of the firebase of my app
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnSuccessListener(ContactsActivity.this, instanceIdResult -> {
                    FirebaseUser user = new FirebaseUser(ConversationRepo.getLoggedUser().getId(),
                            instanceIdResult.getToken());
                    CrossServerAPI crossServerAPI = new CrossServerAPI();
                    crossServerAPI.register(user);
//                    String newToken = instanceIdResult.getToken();
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //set the contact list order by the contact with the last message on the top
        List<Contact> sortListContacts = viewContacts.get().getValue();
        Collections.sort(sortListContacts, Collections.reverseOrder());
        adapter.setLstContent(sortListContacts);
    }

    public void setProfilePic(ImageView profilePic, String id) {
        //get the profile pic from the local database
        Bitmap image = ConversationRepo.GetBitmap(id);
        if(image != null) {
            profilePic.setImageBitmap(image);
        }
        //the default profile pic -> its already in xml

    }


}