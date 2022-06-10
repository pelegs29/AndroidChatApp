package com.example.chatapp.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Conversation {
    @PrimaryKey(autoGenerate = true)
    private int Id;
    private String from;
    private String to;
    List<Content> Contents;

    public Conversation(String from, String to, List<Content> contents) {
        this.from = from;
        this.to = to;
        Contents = contents;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<Content> getContents() {
        return Contents;
    }

    public void setContents(List<Content> contents) {
        Contents = contents;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
