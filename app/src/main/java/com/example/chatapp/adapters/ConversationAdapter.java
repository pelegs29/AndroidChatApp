package com.example.chatapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.entities.Content;

import java.util.List;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConvViewHolder> {

    List<Content> lstContent;
    private final LayoutInflater mInflater;

    public ConversationAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    //create the element that will add to the list to display
    @Override
    public ConvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.mess_layout, parent, false);
        return new ConvViewHolder(itemView);
    }

    //add the new element to the listView/recycle
    @Override
    public void onBindViewHolder(@NonNull ConvViewHolder holder, int position) {
        if (lstContent != null) {
            final Content current = lstContent.get(position);
            holder.mess.setText(current.getContent());
            holder.time.setText(parsJasonToTime(current.getCreated()));
            if (current.isSent()) {
                holder.cardView.setCardBackgroundColor(Color.parseColor("#C8E6C9"));
            } else {
                holder.cardView.setCardBackgroundColor(Color.parseColor("#E3F2FD"));
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) holder.cardView.getLayoutParams();
                layoutParams.endToEnd = 0;
                layoutParams.startToStart = -1;
                layoutParams.startToEnd = -1;
                holder.cardView.setLayoutParams(layoutParams);

                ConstraintLayout constraintLayout = holder.parentLayout;
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);
                constraintSet.connect(R.id.timeMess, ConstraintSet.BOTTOM, R.id.mess_layout_cardMess, ConstraintSet.BOTTOM, 0);
                constraintSet.connect(R.id.timeMess, ConstraintSet.END, R.id.mess_layout_cardMess, ConstraintSet.START, 0);
                constraintSet.connect(R.id.timeMess, ConstraintSet.START, -1, ConstraintSet.END, 0);
                constraintSet.applyTo(constraintLayout);
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

    ;

    class ConvViewHolder extends RecyclerView.ViewHolder {

        private final TextView mess;
        private final TextView time;
        private final CardView cardView;
        private final ConstraintLayout parentLayout;

        public ConvViewHolder(View itemView) {
            //get the element from the xml and create a new obj that contain the inner xml element separate
            super(itemView);
            mess = itemView.findViewById(R.id.messView);
            time = itemView.findViewById(R.id.timeMess);
            cardView = itemView.findViewById(R.id.mess_layout_cardMess);
            parentLayout = itemView.findViewById(R.id.mess_clMain);
        }
    }

    public  String parsJasonToTime(String jsTime){
        String year = jsTime.substring(2,4);
        String month = jsTime.substring(5,7);
        String day = jsTime.substring(8,10);
        String time = jsTime.substring(11,16);
        return time + " " + day + "/" + month +"/" + year;
    }
}

