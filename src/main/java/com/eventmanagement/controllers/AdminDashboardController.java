package com.eventmanagement.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.eventmanagement.services.UserService;
import com.eventmanagement.models.User;
import java.util.List;

public class AdminDashboardController {

    @FXML private Button refreshUsersBtn;
    @FXML private Button addUserBtn;
    @FXML private TableView<User> usersTable;
    @FXML private TableColumn<User, Integer> idColumn;
    @FXML private TableColumn<User, String> usernameColumn;
    @FXML private TableColumn<User, String> emailColumn;
    @FXML private TableColumn<User, String> roleColumn;
    @FXML private TableColumn<User, String> actionsColumn;

    private UserService userService;

    @FXML
    public void initialize() {
        userService = UserService.getInstance();
        setupTableColumns();
        refreshUsers();
        System.out.println("✅ AdminDashboardController initialized");
    }

    @FXML
    private void refreshUsers() {
        System.out.println("🔄 Refreshing users...");
        try {
            List<User> users = userService.getAllUsers();
            usersTable.getItems().clear();
            usersTable.getItems().addAll(users);
            System.out.println("✅ Loaded " + users.size() + " users");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Failed to load users: " + e.getMessage());
        }
    }

    @FXML
    private void showAddUserDialog() {
        System.out.println("➕ Add user dialog (not implemented yet)");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add User");
        alert.setHeaderText("Add New User");
        alert.setContentText("User creation dialog coming soon...");
        alert.showAndWait();
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getUserId()).asObject());
        usernameColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUsername()));
        emailColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmail()));
        roleColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRole().name()));
    }
}
