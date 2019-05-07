package com.example.b_quest;

//CODE BY JUAN MARTIN

// class defines the attributes for the user

public class User {

    private String userID;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phoneNumber;
    private String user_auth_id;


    public User(String firstName, String lastName, String username, String email, String phoneNumber, String userID, String user_auth_id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userID = userID;
        this.user_auth_id = user_auth_id;
    }

    public User() {
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getUser_auth_id() {
        return user_auth_id;
    }

    public void setUser_auth_id(String user_auth_id) {
        this.user_auth_id = user_auth_id;
    }
}

