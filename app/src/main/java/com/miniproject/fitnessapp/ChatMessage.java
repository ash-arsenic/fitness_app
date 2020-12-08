package com.miniproject.fitnessapp;

import android.os.Build;

import java.util.Comparator;
import java.util.Date;

public class ChatMessage {
    private String message;
    private String receiver;
    private String sender;
    private boolean isseen;
    private long messageTime;
    private String senderName;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public ChatMessage(String message, String receiver, String sender, String senderName, String type, boolean isseen) {
        this.message = message;
        this.receiver = receiver;
        this.sender = sender;
        this.isseen = isseen;
        this.senderName = senderName;
        this.type = type;
        messageTime = new Date().getTime();
    }

    public boolean isIsseen() {
        return isseen;
    }

    public void setIsseen(boolean isseen) {
        this.isseen = isseen;
    }

    public ChatMessage() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

}
