package com.example.chatapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.entities.Content;

import java.util.List;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConvViewHolder> {

    List<Content> lstContent;
    private final LayoutInflater mInflater;

    public ConversationAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    //create the element that will add to the list to display
    @Override
    public ConvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.mess_layout, parent,false);
        return new ConvViewHolder(itemView);
    }

    //add the new element to the listView/recycle
    @Override
    public void onBindViewHolder(@NonNull ConvViewHolder holder, int position) {
        if (lstContent!= null){
            final Content  current = lstContent.get(position);
            holder.mess.setText(current.getContent());
            holder.time.setText(current.getCreated());
        }


    }



    @SuppressLint("NotifyDataSetChanged")
    public void setLstContent(List<Content> ls) {
        lstContent = ls;
        notifyDataSetChanged();
    }

    public List<Content> getLstContent() {
        return lstContent;
    }

    @Override
    public  int getItemCount() {
        if(lstContent != null){
             return lstContent.size();
        }
        return 0;
    }

    ;

    class ConvViewHolder extends RecyclerView.ViewHolder {

        private final TextView mess;
        private final TextView time;

        public ConvViewHolder( View itemView) {
            //get the element from the xml and create a new obj that contain the inner xml element separate
            super(itemView);
            mess = itemView.findViewById(R.id.messView);
            time = itemView.findViewById(R.id.timeMess);

        }
    }
}

