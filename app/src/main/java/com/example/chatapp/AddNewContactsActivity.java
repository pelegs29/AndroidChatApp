package com.example.chatapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp.entities.Contact;
import com.example.chatapp.repositories.ConversationRepo;
import com.example.chatapp.viewmodels.ContactsViewModel;

import java.text.DateFormat;
import java.util.Date;

public class AddNewContactsActivity extends AppCompatActivity {
    private ContactsViewModel contactsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_contacts);


        ConversationRepo repo = new ConversationRepo();
        //the item in the page
//
//        Gson gson = new Gson();
//        String contactsListJson = getIntent().getStringExtra("contactsList");
//         contactsViewModel = gson.fromJson(contactsListJson, ContactsViewModel.class);


        EditText username = findViewById(R.id.addUser_username);
        EditText nickName = findViewById(R.id.addUser_nickname);
        EditText server = findViewById(R.id.addUser_server);
        EditText pic = findViewById(R.id.addUser_Pic);

        Button btnSend = findViewById(R.id.addUser_sendbtn);

        btnSend.setOnClickListener(x -> {

            String time = DateFormat.getDateTimeInstance().format(new Date());

            //create a new contact and add
            Contact contact = new Contact(username.getText().toString(), nickName.getText().toString(), server.getText().toString(), null, null, repo.getLoggedUser().getId());
            repo.addContact(contact);

            //back to the contact page
            finish(); //get back to the contact list

        });


    }
}