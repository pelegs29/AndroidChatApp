package com.example.chatapp.entities;

public class Invitation {

    private String from;
    private String to;
    private String server;
    private String name;

    public Invitation(String from, String to, String server, String name) {
        this.from = from;
        this.to = to;
        this.server = server;
        this.name = name;
    }
}
