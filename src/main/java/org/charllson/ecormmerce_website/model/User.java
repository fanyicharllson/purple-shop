package org.charllson.ecormmerce_website.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {
    private int id;
    private String fullName;
    private String email;
    private String password;
    private String profileImagePath;
    private LocalDateTime createdAt;

    // Constructors
    public User() {}

    public User(int id, String fullName, String email, String password, String profileImagePath, LocalDateTime createdAt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.profileImagePath = profileImagePath;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getProfileImagePath() { return profileImagePath; }
    public void setProfileImagePath(String profileImagePath) { this.profileImagePath = profileImagePath; }

    public LocalDateTime getCreatedAt1() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getCreatedAt() {
        if (createdAt != null) {
            return createdAt.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
        }
        return "Unknown";
    }
}