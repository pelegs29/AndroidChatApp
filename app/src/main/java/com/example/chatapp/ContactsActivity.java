package com.example.chatapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.chatapp.adapters.ContactsAdapter;
import com.example.chatapp.adapters.ConversationAdapter;
import com.example.chatapp.entities.Contact;
import com.example.chatapp.repositories.ConversationRepo;
import com.example.chatapp.viewmodels.ContactsViewModel;
import com.example.chatapp.viewmodels.ConversationViewModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ContactsActivity extends AppCompatActivity {

    private ConversationRepo repo;
    private ContactsViewModel viewContacts;
    ContactsAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        String id_UserLogged = "nadav";

        repo = new ConversationRepo();

        ConversationRepo.setLoggedUser(id_UserLogged);


        viewContacts =  new ViewModelProvider(this).get(ContactsViewModel.class);

        //the convList - hold the content in the win
        RecyclerView contactList = findViewById(R.id.contact_rcContact);

        this.adapter = new ContactsAdapter(this);
        contactList.setAdapter(adapter); // connect the adapter to the RecyclerView
        contactList.setLayoutManager(new LinearLayoutManager(this)); // make the item in the RecyclerView appear in liner order

        List<Contact> sortListContacts = repo.getContactList();
        Collections.sort(sortListContacts,Collections.reverseOrder());

        viewContacts.getCon().setValue(sortListContacts);
        adapter.setLstContent(sortListContacts);


        // ls - contain the update Content List
        viewContacts.getCon().observe(this, ls -> adapter.setLstContent(ls) );

        // set the function when the user click on item in the list
        contactList.setClickable(true);

        Button addBtn = findViewById(R.id.contacts_add);
        addBtn.setOnClickListener(v -> {
            Intent i = new Intent(this,AddNewContacts.class);
            startActivity(i);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //set the contact list order by the contact with the last message on the top
        List<Contact> sortListContacts = repo.getContactList();
        Collections.sort(sortListContacts,Collections.reverseOrder());
        adapter.setLstContent(sortListContacts);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu,menu);
//        MenuItem menuItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView)  menuItem.getActionView();
//        searchView.setQueryHint("Search here");
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//            //if the user click on the icon
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            //action that take place on every key type on the input
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                List<Contact> curr =  adapter.getLstContent().stream().filter(x -> x.getId() == newText).collect(Collectors.toList());
//                adapter.setLstContent(curr);
//
//                return false;
//            }
//        });
//
//        return super.onCreateOptionsMenu(menu);
//    }


}