package com.example.chatapp.entities;

public class Contact {
    private String Id;
    private String Name;
    private String Server;
    private String last;
    private String lastdate;

    public Contact(String id, String name, String server, String last, String lastdate) {
        Id = id;
        Name = name;
        Server = server;
        this.last = last;
        this.lastdate = lastdate;
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

    public String getServer() {
        return Server;
    }

    public void setServer(String server) {
        Server = server;
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
}
