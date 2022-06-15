package com.example.chatapp;

import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.adapters.ConversationAdapter;
import com.example.chatapp.entities.Contact;
import com.example.chatapp.entities.Content;
import com.example.chatapp.repositories.ConversationRepo;
import com.example.chatapp.viewmodels.ContactsViewModel;
import com.example.chatapp.viewmodels.ConversationViewModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ConversationActivity extends AppCompatActivity {

    private ConversationViewModel viewConversation;
    private ContactsViewModel contactsViewModel;
    private String to; //the friend id

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        if (getIntent().hasExtra("friendID")) {
            to = getIntent().getStringExtra("friendID");
            ConversationViewModel.setFriend(to);
        }
        contactsViewModel = new ViewModelProvider(this).get((ContactsViewModel.class));

        Contact friend = contactsViewModel.getContactById(to);

        //set the friend name on the top
        TextView friendName = findViewById(R.id.userNameCon);
        friendName.setText(friend.getName());

        viewConversation = new ViewModelProvider(this).get(ConversationViewModel.class);

        ImageButton sendBtn = findViewById(R.id.SendButtun);
        EditText input = findViewById(R.id.inputWin);

        //the convList - hold the content in the win
        RecyclerView convList = findViewById(R.id.chatListWin);

        final ConversationAdapter adapter = new ConversationAdapter(this);
        convList.setAdapter(adapter); // connect the adapter to the RecyclerView
        convList.setLayoutManager(new LinearLayoutManager(this)); // make the item in the RecyclerView appear in liner order

        //when click the send button
        sendBtn.setOnClickListener(v -> {
            addContent(to, input.getText().toString());
            convList.scrollToPosition(adapter.getItemCount() - 1);
        });

        adapter.setLstContent(viewConversation.get().getValue());

        // ls - contain the update Content List
        viewConversation.get().observe(this, ls -> adapter.setLstContent(ls));

        convList.scrollToPosition(adapter.getItemCount() - 1);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addContent(String to, String text) {
        String timeNew = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")).toString();
        Content newCon = new Content(ConversationRepo.getLoggedUser().getId(), to, text, timeNew, true);
        viewConversation.addContent(newCon);
    }
}