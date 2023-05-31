package com.example.trocapp.messages;

public class MessageList {
    private String name, userId, lastMessages,profilePic,chatKey;
    private int unseenMessages;
    public MessageList(String name, String userId, String lastMessages, int unseenMessages, String profilePic,String chatKey) {
        this.name = name;
        this.userId = userId;
        this.lastMessages = lastMessages;
        this.unseenMessages = unseenMessages;
        this.profilePic=profilePic;
        this.chatKey=chatKey;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public String getLastMessages() {
        return lastMessages;
    }

    public int getUnseenMessages() {
        return unseenMessages;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getChatKey() {
        return chatKey;
    }
}
