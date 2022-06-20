package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.chatapp.entities.Contact;
import com.example.chatapp.entities.Conversation;
import com.example.chatapp.repositories.ConversationRepo;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNewContacts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_contacts);

        ConversationRepo repo = new ConversationRepo();
        //the item in the page

        EditText name = findViewById(R.id.addUser_username);
        EditText nickName = findViewById(R.id.addUser_username);
        EditText server = findViewById(R.id.addUser_server);
        EditText pic = findViewById(R.id.addUser_Pic);

        Button btnSend = findViewById(R.id.addUser_sendbtn);

        btnSend.setOnClickListener(x->{

            String time = DateFormat.getDateTimeInstance().format(new Date());

            //create a new contact and add
            Contact contact = new Contact(name.getText().toString(),nickName.getText().toString(),server.getText().toString(),"","".toString(),repo.getLoggedUser().getId());
            repo.addContact(contact);

            //back to the contact page
            finish(); //get back to the contact list

//            Intent intent = new Intent(this,ContactsActivity.class);
//            startActivity(intent);
        } );


    }
}