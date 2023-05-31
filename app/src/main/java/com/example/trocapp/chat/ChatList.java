package com.example.trocapp.chat;

public class ChatList {
    private String userId, name,  message,  date,time;

    public ChatList(String userId, String name, String message, String date, String time) {
        this.userId = userId;
        this.name = name;
        this.message = message;
        this.date = date;
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
