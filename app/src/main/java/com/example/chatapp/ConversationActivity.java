package com.example.chatapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.chatapp.adapters.ConversationAdapter;
import com.example.chatapp.entities.Contact;
import com.example.chatapp.repositories.ConversationRepo;
import com.example.chatapp.viewmodels.ConversationViewModel;


public class ConversationActivity extends AppCompatActivity {

    private ConversationViewModel viewConversation;
    private ConversationRepo repo;
    private String from ; // the user logged id
    private String to ; //the friend id

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);


        from = "nadav";
        to = "peleg";

        repo = new ConversationRepo();
        Contact friend = repo.getContact(to);

        //set the friend name on the top
        TextView friendName = findViewById(R.id.userNameCon);
        friendName.setText(friend.getName());

        TextView lastSeen =  findViewById(R.id.lastseen);
        lastSeen.setText(friend.getLastdate());


        viewConversation =  new ViewModelProvider(this).get(ConversationViewModel.class);

        ImageButton sendBtn = findViewById(R.id.SendButtun);
        EditText input = findViewById(R.id.inputWin);

        sendBtn.setOnClickListener(v -> viewConversation.addContent(input.getText().toString()));

        //set the friend contact info on the top of the win


        //the convList - hold the content in the win
        RecyclerView convList = findViewById(R.id.chatListWin);

        final ConversationAdapter adapter = new ConversationAdapter(this);
        convList.setAdapter(adapter); // connect the adapter to the RecyclerView
        convList.setLayoutManager(new LinearLayoutManager(this)); // make the item in the RecyclerView appear in liner order

        viewConversation.getCon().setValue(repo.getConv(from,to).getContents());
        adapter.setLstContent(repo.getConv(from,to).getContents());

        // ls - contain the update Content List
        viewConversation.getCon().observe(this, ls -> adapter.setLstContent(ls) );


    }
}