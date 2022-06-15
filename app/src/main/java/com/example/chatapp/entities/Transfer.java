package com.example.chatapp.entities;

public class Transfer {
    private String from;
    private String to;
    private String content;

    public Transfer(String from, String to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }
}
