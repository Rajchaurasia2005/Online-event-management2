package com.eventmanagement.controllers;
import com.eventmanagement.models.User;
import com.eventmanagement.services.AuthenticationService;
import com.eventmanagement.utils.PasswordUtil;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import com.eventmanagement.utils.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton;

    private AuthenticationService authService;
    private SessionManager sessionManager;

    @FXML
    public void initialize() {
        authService = AuthenticationService.getInstance();
        sessionManager = SessionManager.getInstance();

        // Initialize demo accounts if needed
        initializeDemoAccounts();
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        System.out.println("🔍 Login attempt: " + username);

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Username and password are required!");
            return;
        }

        User user = authService.getUserByUsername(username);  // Get user first
        if (user == null) {
            System.out.println("❌ User not found: " + username);
            errorLabel.setText("Invalid username or password!");
            passwordField.clear();
            return;
        }

        System.out.println("✅ User found: " + user.getUsername());
        System.out.println("🔑 Stored hash: " + user.getPassword().substring(0, 20) + "...");

        boolean isValid = PasswordUtil.verifyPassword(password, user.getPassword());
        System.out.println("🔐 Password valid: " + isValid);

        if (isValid) {
            String sessionId = authService.login(username, password);
            if (sessionId != null) {
                System.out.println("🎉 Login successful! Session: " + sessionId);
                loadDashboard(sessionId, user);
            } else {
                errorLabel.setText("Login failed - session error!");
            }
        } else {
            System.out.println("❌ Password mismatch!");
            errorLabel.setText("Invalid username or password!");
            passwordField.clear();
        }
    }

    private void loadDashboard(String sessionId, User user) {
        try {
            String fxmlPath;

            // ✅ Java 11 compatible switch
            switch (user.getRole()) {
                case ADMIN:
                    fxmlPath = "/fxml/admin-dashboard.fxml";
                    break;
                case ORGANIZER:
                    fxmlPath = "/fxml/organizer-dashboard.fxml";
                    break;
                case ATTENDEE:
                    fxmlPath = "/fxml/attendee-dashboard.fxml";
                    break;
                default:
                    throw new IllegalArgumentException("Unknown role: " + user.getRole());
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene newScene = new Scene(loader.load(), 900, 700);

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(newScene);
            stage.setTitle("Event Management - " + user.getRole());
            stage.show();

            System.out.println("✅ Dashboard loaded for: " + user.getRole());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Failed to load dashboard: " + e.getMessage());
        }
    }

    private void initializeDemoAccounts() {
        // This method initializes demo accounts if they don't exist
        // In production, this should be done during initial setup only
    }
}
