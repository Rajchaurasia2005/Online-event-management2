package com.eventmanagement.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

public class AttendeeDashboardController {
    @FXML
    private Label userInfoLabel;

    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {
        userInfoLabel.setText("Attendee");
        showBrowseEvents();
    }

    @FXML
    private void showBrowseEvents() {
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        Label label = new Label("Browse Available Events");
        label.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        content.getChildren().add(label);

        contentArea.getChildren().clear();
        contentArea.getChildren().add(content);
    }

    @FXML
    private void showMyRegistrations() {
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        Label label = new Label("My Event Registrations");
        label.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        content.getChildren().add(label);

        contentArea.getChildren().clear();
        contentArea.getChildren().add(content);
    }

    @FXML
    private void showTicketHistory() {
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        Label label = new Label("Ticket Purchase History");
        label.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        content.getChildren().add(label);

        contentArea.getChildren().clear();
        contentArea.getChildren().add(content);
    }

    @FXML
    private void showMessages() {
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        Label label = new Label("Event Updates & Messages");
        label.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        content.getChildren().add(label);

        contentArea.getChildren().clear();
        contentArea.getChildren().add(content);
    }

    @FXML
    private void handleLogout() {
        // Handle logout
    }
}
