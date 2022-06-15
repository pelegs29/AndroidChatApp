package com.example.chatapp.entities;

public class FirebaseUser {
    private String username;
    private String token;

    public FirebaseUser(String username, String token) {
        this.username = username;
        this.token = token;
    }
}

