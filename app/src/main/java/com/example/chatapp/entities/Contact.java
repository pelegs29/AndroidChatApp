package com.example.chatapp.entities;

import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact implements Comparable<Contact>  {
    public void setDataId(int dataId) {
        DataId = dataId;
    }

    @PrimaryKey(autoGenerate = true)
    private int DataId;
    private String id;
    private String name;
    private String server;
    private String last;
    private String lastdate;
    private String contactOf; // the user that this contact is his contact

    public String getContactOf() {
        return contactOf;
    }


    public Contact(String id, String name, String server, String last, String lastdate, String contactOf) {
        this.id = id;
        this.name = name;
        this.server = server;
        this.last = last;
        this.lastdate = lastdate;
        this.contactOf = contactOf;
    }

    public int getDataId() {
        return DataId;
    }

    public void setContactOf(String contactOf) {
        this.contactOf = contactOf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getLastdate() {
        return lastdate;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }


    @Override
    public int compareTo(Contact o) {
        //in case that a new contact the has not last message
        if(this.last == null){
            return 1;
        }
        if(o.getLastdate() == null){
         return 1;
        }
        return this.getLastdate().compareTo(o.getLastdate());
    }
}
