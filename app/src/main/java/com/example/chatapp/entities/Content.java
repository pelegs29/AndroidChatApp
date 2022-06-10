package com.example.chatapp.entities;

public class Content {
    private int Id;
    private String Content;
    private String Created;
    private boolean Sent;

    public Content(int id, String content, String created, boolean sent) {
        Id = id;
        Content = content;
        Created = created;
        Sent = sent;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String created) {
        Created = created;
    }

    public boolean isSent() {
        return Sent;
    }

    public void setSent(boolean sent) {
        Sent = sent;
    }
}
