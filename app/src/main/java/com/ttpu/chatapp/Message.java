package com.ttpu.chatapp;


public class Message {
    private String sender;
    private String receiver;
    private String message;
    private String timestamp;
    private String author;
    TimeStamp time  = new TimeStamp();
    public Message(String sender, String receiver, String message, String timestamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.timestamp = timestamp;
    }
    public Message(String sender, String message, String timestamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.timestamp = timestamp;
    }
public Message(){}

    public String getSender() {
        return sender;
    }


    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {

        return receiver;
    }



    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp=timestamp;;
    }


    public String getAuthor(){
        return sender ;
    }
}
