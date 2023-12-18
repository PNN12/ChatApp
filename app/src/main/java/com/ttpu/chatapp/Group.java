package com.ttpu.chatapp;


import java.util.ArrayList;

@SuppressWarnings("ALL")
public class Group {
    private String name;
    private ArrayList<User> members;
    private ArrayList<Message> messages;

// --Commented out by Inspection START (14.05.2023 15:06):
//    public Group(String name) {
//        this.name = name;
//        members = new ArrayList<>();
//        messages = new ArrayList<>();
//    }
// --Commented out by Inspection STOP (14.05.2023 15:06)

// --Commented out by Inspection START (14.05.2023 15:06):
//    public String getName() {
//        return name;
//    }
// --Commented out by Inspection START (14.05.2023 15:06):
//// --Commented out by Inspection STOP (14.05.2023 15:06)
//
//// --Commented out by Inspection START (14.05.2023 15:06):
// --Commented out by Inspection START (14.05.2023 15:06):
//// --Commented out by Inspection STOP (14.05.2023 15:06)
//// --Commented out by Inspection START (14.05.2023 15:06):
// --Commented out by Inspection STOP (14.05.2023 15:06)
////    public ArrayList<User> getMembers() {
////        return members;
////    }
//// --Commented out by Inspection STOP (14.05.2023 15:06)
// --Commented out by Inspection STOP (14.05.2023 15:06)

    public ArrayList<Message> getMessages() {
        return messages;
    }

// --Commented out by Inspection START (14.05.2023 15:06):
//    public void addMember(User user) {
//        members.add(user);
//    }
// --Commented out by Inspection START (14.05.2023 15:06):
//// --Commented out by Inspection STOP (14.05.2023 15:06)
//
//    public void removeMember(User user) {
// --Commented out by Inspection STOP (14.05.2023 15:06)
//        members.remove(user);
//    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void removeMessage(Message message) {
        messages.remove(message);
    }
}
