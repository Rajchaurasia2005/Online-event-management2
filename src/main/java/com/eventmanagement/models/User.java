package com.eventmanagement.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private int userId;           // ← INT for database user_id

    private String username;
    private String password; // Hashed
    private String email;
    private String fullName;
    private UserRole role;
    private boolean isActive;
    private LocalDateTime createdDate;
    private LocalDateTime lastModified;

    public enum UserRole {
        ADMIN, ORGANIZER, ATTENDEE
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }  // INT

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }  // STRING

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", role=" + role +
                ", isActive=" + isActive +
                '}';
    }
}