package com.example.chatapp.entities;

public class Content {
    private int Id;
    private int From;
    private int To;
    private String Time;
    private String Type;
    private String Message;

    public Content(int id, int from, int to, String time, String type, String message) {
        Id = id;
        From = from;
        To = to;
        Time = time;
        Type = type;
        Message = message;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getFrom() {
        return From;
    }

    public void setFrom(int from) {
        From = from;
    }

    public int getTo() {
        return To;
    }

    public void setTo(int to) {
        To = to;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
