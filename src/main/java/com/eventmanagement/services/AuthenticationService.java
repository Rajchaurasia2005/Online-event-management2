package com.eventmanagement.services;
import java.util.UUID;

import com.eventmanagement.models.User;
import com.eventmanagement.dao.UserDAO;
import com.eventmanagement.utils.PasswordUtil;
import com.eventmanagement.utils.SessionManager;
import com.eventmanagement.utils.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    private static AuthenticationService instance;
    private UserDAO userDAO;
    private SessionManager sessionManager;

    private AuthenticationService() {
        this.userDAO = new UserDAO();
        this.sessionManager = SessionManager.getInstance();
    }
    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }


    public static synchronized AuthenticationService getInstance() {
        if (instance == null) {
            instance = new AuthenticationService();
        }
        return instance;
    }

    public String login(String username, String password) {
        User user = getUserByUsername(username);  // Now this works
        if (user != null && PasswordUtil.verifyPassword(password, user.getPassword())) {
            String sessionId = UUID.randomUUID().toString();  // Now compiles
            sessionManager.createSession(sessionId , user);
            System.out.println("Session created: " + sessionId + " for user: " + username);
            return sessionId;
        }
        return null;
    }

    public boolean logout(String sessionId) {
        if (sessionId != null && sessionManager.isSessionValid(sessionId)) {
            sessionManager.invalidateSession(sessionId);
            logger.info("User logged out successfully");
            return true;
        }
        return false;
    }

    public User registerUser(String username, String email, String password,
                             String fullName, User.UserRole role) {

        // Validate inputs
        if (!ValidationUtil.isValidUsername(username)) {
            logger.warn("Registration failed - invalid username: {}", username);
            return null;
        }
        if (!ValidationUtil.isValidEmail(email)) {
            logger.warn("Registration failed - invalid email: {}", email);
            return null;
        }
        if (!PasswordUtil.isValidPassword(password)) {
            logger.warn("Registration failed - weak password for user: {}", username);
            return null;
        }
        if (!ValidationUtil.isValidFullName(fullName)) {
            logger.warn("Registration failed - invalid full name");
            return null;
        }

        // Check if user already exists
        if (userDAO.getUserByUsername(username) != null) {
            logger.warn("Registration failed - username already exists: {}", username);
            return null;
        }
        if (userDAO.getUserByEmail(email) != null) {
            logger.warn("Registration failed - email already exists: {}", email);
            return null;
        }

        // Create new user
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(PasswordUtil.hashPassword(password));
        newUser.setFullName(fullName);
        newUser.setRole(role);

        if (userDAO.createUser(newUser)) {
            logger.info("User registered successfully: {}", username);
            return newUser;
        }
        return null;
    }

    public User getCurrentUser(String sessionId) {
        return sessionManager.getSessionUser(sessionId);
    }

    public boolean isSessionValid(String sessionId) {
        return sessionManager.isSessionValid(sessionId);
    }
}