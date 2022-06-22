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

        EditText username = findViewById(R.id.addUser_username);
        EditText nickName = findViewById(R.id.addUser_nickname);
        EditText server = findViewById(R.id.addUser_server);
        EditText pic = findViewById(R.id.addUser_Pic);

        Button btnSend = findViewById(R.id.addUser_sendbtn);


        //when the user click on the button
        btnSend.setOnClickListener(x -> {
            String time = DateFormat.getDateTimeInstance().format(new Date());
            if (!checkServer(server.getText().toString())) {
                server.setError("Invalid server - the correct format is: localhost:XXXX");
                server.requestFocus();
                return;
            }
            //create a new contact and add
            Contact contact = new Contact(username.getText().toString(), nickName.getText().toString(), server.getText().toString(), null, null, repo.getLoggedUser().getId());
            repo.addContact(contact);
            //back to the contact page
            finish(); //get back to the contact list
        });
    }

    //function that check if the server string is start with localhost:  followed by only 4 digits using regex
    //if so, return true
    //else return false
    public boolean checkServer(String server) {
        String regex = "^localhost:\\d{4}$";
        if (server.matches(regex)) {
            return true;
        }
        return false;
    }


}