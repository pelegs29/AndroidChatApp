package com.example.chatapp.entities;

import java.util.List;

public class User {
    private String id;
    private String name;
    private String password;
    private List<Contact> contacts;

    public User(String id, String name, String password, List<Contact> contacts) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.contacts = contacts;
    }

    public User(String id, String password){
        this.id = id;
        this.name = null;
        this.password = password;
        this.contacts = null;
    }

    public User(String fullname, String id, String password){
        this.id = id;
        this.name = fullname;
        this.password = password;
        this.contacts = null;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}
