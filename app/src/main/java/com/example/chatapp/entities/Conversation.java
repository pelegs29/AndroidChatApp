package com.example.chatapp.entities;

import java.util.List;

//@Entity
public class Conversation {
//    @PrimaryKey(autoGenerate = true)
    private int id;
    private String from;
    private String to;
    List<Content> contents;


    public Conversation(String from, String to, List<Content> contents) {
        this.from = from;
        this.to = to;
        this.contents = contents;
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
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
