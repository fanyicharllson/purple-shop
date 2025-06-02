package org.charllson.ecormmerce_website.utils;

/**
 * Utility class to manage user session across the application
 */
public class SessionManager {
    private static SessionManager instance;
    private int currentUserId = -1;
    private String currentUserEmail;
    private String currentUserName;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    // Set user session after successful login/signup
    public void setUserSession(int userId, String email, String name) {
        this.currentUserId = userId;
        this.currentUserEmail = email;
        this.currentUserName = name;
    }

    // Clear user session on logout
    public void clearSession() {
        this.currentUserId = -1;
        this.currentUserEmail = null;
        this.currentUserName = null;
    }

    // Check if a user is logged in
    public boolean isLoggedIn() {
        return currentUserId != -1;
    }

    // Getters
    public int getCurrentUserId() {
        return currentUserId;
    }

    public String getCurrentUserEmail() {
        return currentUserEmail;
    }

    public String getCurrentUserName() {
        return currentUserName;
    }
}