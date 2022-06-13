package com.example.chatapp.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Content {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String from;
    private String to;
    private String content;
    private String created;
    private boolean sent;

    public Content() {
    }



    public Content(String from, String to, String content, String created, boolean sent) {
        this.content = content;
        this.created = created;
        this.sent = sent;
        this.from = from;
        this.to = to;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
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
}
