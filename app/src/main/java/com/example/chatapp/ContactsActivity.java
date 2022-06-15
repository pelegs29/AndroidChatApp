package com.example.chatapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.adapters.ContactsAdapter;
import com.example.chatapp.entities.Contact;
import com.example.chatapp.repositories.ConversationRepo;
import com.example.chatapp.viewmodels.ContactsViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Collections;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    private ConversationRepo repo;
    private ContactsViewModel viewContacts;
    ContactsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        viewContacts =  new ViewModelProvider(this).get(ContactsViewModel.class);

        viewContacts.setUpContacts();

        //the convList - hold the content in the win
        RecyclerView contactList = findViewById(R.id.contact_rcContact);

        this.adapter = new ContactsAdapter(this);
        contactList.setAdapter(adapter); // connect the adapter to the RecyclerView
        contactList.setLayoutManager(new LinearLayoutManager(this)); // make the item in the RecyclerView appear in liner order
        List<Contact> sortListContacts = viewContacts.get().getValue();
        Collections.sort(sortListContacts,Collections.reverseOrder());
//        viewContacts.getCon().setValue(sortListContacts);
        adapter.setLstContent(sortListContacts);
        // ls - contain the update Content List
        viewContacts.get().observe(this, ls -> adapter.setLstContent(ls) );
        // set the function when the user click on item in the list
        contactList.setClickable(true);
        FloatingActionButton addBtn = findViewById(R.id.contacts_add);
        addBtn.setOnClickListener(v -> {
            Intent i = new Intent(this,AddNewContacts.class);
            startActivity(i);
        });

        //contact to fireBase
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(ContactsActivity.this, new OnSuccessListener<InstanceIdResult>() {

            //get the token of the firebase of my app
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String  newToken = instanceIdResult.getToken();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        //set the contact list order by the contact with the last message on the top
        List<Contact> sortListContacts = viewContacts.get().getValue();
        Collections.sort(sortListContacts,Collections.reverseOrder());
        adapter.setLstContent(sortListContacts);
    }
}