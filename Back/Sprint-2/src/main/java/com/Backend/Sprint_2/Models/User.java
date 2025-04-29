package com.Backend.Sprint_2.Models;

import com.google.cloud.firestore.annotation.DocumentId;

public class User {

    @DocumentId
    private String id;
    private String name;
    private String password;
    private UserRole role;
    private UserLevel level;
    private boolean available;

    public enum UserRole{
        Leader,
        Programmer
    }

    public enum UserLevel{
        Junior,
        Senior
    }

    public User() {}

    public User (String id, String name, String password, UserRole role, UserLevel level, boolean available) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
        this.level = level;
        this.available = available;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserLevel getLevel() {
        return level;
    }

    public void setLevel(UserLevel level) {
        this.level = level;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
