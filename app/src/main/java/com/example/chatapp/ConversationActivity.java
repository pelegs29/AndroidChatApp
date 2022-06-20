package com.example.chatapp;

import static android.content.ContentValues.TAG;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
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
    private ConversationAdapter adapter;
    private RecyclerView convList;

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
        firebaseService.setConversationViewModel(viewConversation);

        ImageButton sendBtn = findViewById(R.id.SendButtun);
        EditText input = findViewById(R.id.inputWin);

        //the convList - hold the content in the win
        convList = findViewById(R.id.chatListWin);

        adapter = new ConversationAdapter(this);
        convList.setAdapter(adapter); // connect the adapter to the RecyclerView
        convList.setLayoutManager(new LinearLayoutManager(this)); // make the item in the RecyclerView appear in liner order

        //when click the send button
        sendBtn.setOnClickListener(v -> {
            if (input.getText().toString().equals("")) {
                return;
            }
            addContent(to, input.getText().toString());
            input.setText("");
            convList.scrollToPosition(adapter.getItemCount() - 1);
        });

        adapter.setLstContent(viewConversation.get().getValue());

        // ls - contain the update Content List
        viewConversation.get().observe(this, ls -> {
            adapter.setLstContent(ls);
            convList.scrollToPosition(adapter.getItemCount() - 1);
        });

        convList.addOnLayoutChangeListener((v, left, top, right, bottom,
                                            oldLeft, oldTop, oldRight, oldBottom) -> {
            if (bottom < oldBottom) {
                convList.postDelayed(() ->
                        convList.scrollToPosition(adapter.getItemCount() - 1), 100);
            }
        });

        input.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                sendBtn.callOnClick();
                return true;
            }
            return false;
        });

        Log.i(TAG, "onCreate: foo" + adapter.getItemCount());


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addContent(String to, String text) {
        String timeNew = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")).toString();
        Content newCon = new Content(ConversationRepo.getLoggedUser().getId(), to, text, timeNew, true);
        viewConversation.addContent(newCon);
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseService.setConversationViewModel(viewConversation);
        //convList.scrollToPosition(adapter.getItemCount() - 1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        firebaseService.setConversationViewModel(null);
    }


}