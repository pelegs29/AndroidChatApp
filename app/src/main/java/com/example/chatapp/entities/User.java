package com.example.chatapp.entities;

import java.util.List;

public class User {
    private String Id;
    private String Name;
    private String Password;
    private List<Contact> Contacts;

    public User(String id, String name, String password, List<Contact> contacts) {
        Id = id;
        Name = name;
        Password = password;
        Contacts = contacts;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public List<Contact> getContacts() {
        return Contacts;
    }

    public void setContacts(List<Contact> contacts) {
        Contacts = contacts;
    }
}
