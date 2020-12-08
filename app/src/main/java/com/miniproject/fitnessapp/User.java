package com.miniproject.fitnessapp;

public class User {
    public String username;
    public String id;

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public User(String username, String id) {
        this.username = username;
        this.id = id;
    }
}
