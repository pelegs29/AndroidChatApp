package com.example.chatapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.ConversationActivity;
import com.example.chatapp.R;
import com.example.chatapp.entities.Contact;
import com.example.chatapp.entities.Content;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder> {

    List<Contact> lstContacts;
    private final LayoutInflater mInflater;
    String IdUserLogged = "nadav";

    public ContactsAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    //create the element that will add to the list to display
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.singlecontact_layout, parent,false);
        return new ContactViewHolder(itemView);
    }

    //add the new element to the listView/recycle
    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        if (lstContacts!= null){
            final Contact  current = lstContacts.get(position);
            holder.lastMess.setText(current.getLast());
            holder.name.setText(current.getName());
            holder.time.setText(current.getLastdate());

            holder.itemView.setOnClickListener(v -> {
              Intent intent = new Intent(holder.itemView.getContext(),ConversationActivity.class);
                intent.putExtra("name", current.getId());
                intent.putExtra("logged_Id",this.IdUserLogged);
                holder.itemView.getContext().startActivities(new Intent[]{intent});
            });
        }
    }



    @SuppressLint("NotifyDataSetChanged")
    public void setLstContent(List<Contact> ls) {
        lstContacts = ls;
        notifyDataSetChanged();
    }

    public List<Contact> getLstContent() {
        return lstContacts;
    }

    @Override
    public  int getItemCount() {
        if(lstContacts != null){
            return lstContacts.size();
        }
        return 0;
    }

    ;

    class ContactViewHolder extends RecyclerView.ViewHolder {

        private final TextView name; //the name of the contact
        private final TextView time; // the time of the last message
        private final TextView lastMess; // the last message


        public ContactViewHolder( View itemView) {
            //get the element from the xml and create a new obj that contain the inner xml element separate
            super(itemView);
            name = itemView.findViewById(R.id.singleContact_tvName);
            time = itemView.findViewById(R.id.singleContact_tvTimeMess);
            lastMess = itemView.findViewById(R.id.singleContact_tvLastMes);
        }
    }
}

