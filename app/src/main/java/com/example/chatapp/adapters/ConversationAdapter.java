package com.example.chatapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.entities.Content;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConvViewHolder> {

    List<Content> lstContent;
    private final LayoutInflater mInflater;
    //for the time
    String yearNow;
    int monthNow;
    int dayNow;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ConversationAdapter(Context context) {
        mInflater = LayoutInflater.from(context);

        //get the time running now for the time of the conversation
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.yearNow = String.valueOf(localDate.getYear()).substring(2, 4);
        this.monthNow = localDate.getMonthValue();
        this.dayNow = localDate.getDayOfMonth();
    }

    abstract class ConvViewHolder extends RecyclerView.ViewHolder {
        public ConvViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class myConvViewHolder extends ConvViewHolder {

        private final TextView mess;
        private final TextView time;
        private final CardView cardView;
        private final ConstraintLayout parentLayout;

        public myConvViewHolder(View itemView) {
            //get the element from the xml and create a new obj that contain the inner xml element separate
            super(itemView);
            mess = itemView.findViewById(R.id.messView);
            time = itemView.findViewById(R.id.timeMess);
            cardView = itemView.findViewById(R.id.mess_layout_cardMess);
            parentLayout = itemView.findViewById(R.id.mess_clMain);
        }
    }

    class friendConvViewHolder extends ConvViewHolder {

        private final TextView mess;
        private final TextView time;
        private final CardView cardView;
        private final ConstraintLayout parentLayout;

        public friendConvViewHolder(View itemView) {
            //get the element from the xml and create a new obj that contain the inner xml element separate
            super(itemView);
            mess = itemView.findViewById(R.id.friend_messView);
            time = itemView.findViewById(R.id.friend_timeMess);
            cardView = itemView.findViewById(R.id.friend_mess_layout_cardMess);
            parentLayout = itemView.findViewById(R.id.friend_mess_clMain);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (lstContent.get(position).isSent()) {
            return 1;
        }
        return 0;
    }

    //create the element that will add to the list to display
    @Override
    public ConvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        //im the sender of the message
        if (viewType == 1) {
            itemView = mInflater.inflate(R.layout.message_layout, parent, false);
            return new myConvViewHolder(itemView);
        } else {
            itemView = mInflater.inflate(R.layout.friend_message_layout, parent, false);
            return new friendConvViewHolder(itemView);
        }
    }

    //add the new element to the listView/recycle
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ConvViewHolder holder, int position) {
        if (lstContent != null) {
            final Content current = lstContent.get(position);
            if (holder instanceof myConvViewHolder) {
                ((myConvViewHolder) holder).mess.setText(current.getContent());
                ((myConvViewHolder) holder).time.setText(parsJasonToTime(current.getCreated()));
            } else {
                ((friendConvViewHolder) holder).mess.setText(current.getContent());
                ((friendConvViewHolder) holder).time.setText(parsJasonToTime(current.getCreated()));
            }
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
    public int getItemCount() {
        if (lstContent != null) {
            return lstContent.size();
        }
        return 0;
    }


    //determine how the date of the message will be display
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String parsJasonToTime(String jsTime) {
        String year = jsTime.substring(2, 4);
        String month = jsTime.substring(5, 7);
        String day = jsTime.substring(8, 10);
        String time = jsTime.substring(11, 16);

        if (year.equals(yearNow) && Integer.parseInt(month) == monthNow && Integer.parseInt(day) == dayNow) {
            return time;
        }
        if (year.equals(yearNow)) {
            return time + " " + day + "/" + month;

        }
        return time + " " + day + "/" + month + "/" + year;
    }
}

