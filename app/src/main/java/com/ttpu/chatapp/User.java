package com.ttpu.chatapp;



import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;


public class User implements Serializable {
    private String username;
    private String password;
    private String email;
    private String profilePicture;
    private String userId; // добавлено

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public static User getCurrentUser() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            String userId = firebaseUser.getUid(); // добавлено
            String username = firebaseUser.getDisplayName();
            String email = firebaseUser.getEmail();

            User currentUser = new User(username, getCurrentUser().getPassword(), email);
            currentUser.setUserId(userId); // добавлено
            return currentUser;
        } else {
            return null;
        }
    }


    public User() {}

    public User(String email) {
        this.email = email;
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getUserId() { // добавлено
        return userId;
    }

    public void setUserId(String userId) { // добавлено
        this.userId = userId;
    }
}
