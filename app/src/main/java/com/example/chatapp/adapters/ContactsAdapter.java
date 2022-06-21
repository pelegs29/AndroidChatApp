package com.example.chatapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.ConversationActivity;
import com.example.chatapp.R;
import com.example.chatapp.entities.Contact;
import com.example.chatapp.entities.Content;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder> {

    List<Contact> lstContacts;
    private final LayoutInflater mInflater;
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        if (lstContacts!= null){
            final Contact  current = lstContacts.get(position);
            holder.name.setText(current.getName());
            if(current.getLast()== null){
                //this is a new contacts that has no last message
                holder.lastMess.setText("");
            }else {
                holder.lastMess.setText(current.getLast());
            }
            holder.time.setText(parsJasonToTime(current.getLastdate()));

            holder.itemView.setOnClickListener(v -> {
              Intent intent = new Intent(holder.itemView.getContext(),ConversationActivity.class);
                intent.putExtra("friendID", current.getId());
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

    //determine how the date of the message will be display
    public  String parsJasonToTime(String jsTime){
        if(jsTime == null ){
            //in case this is a new Contact that has no messages
            return "";
        }
        String year = jsTime.substring(2,4);
        String month = jsTime.substring(5,7);
        String day = jsTime.substring(8,10);
        String time = jsTime.substring(11,16);
        return time + " " + day + "/" + month +"/" + year;
    }
}

