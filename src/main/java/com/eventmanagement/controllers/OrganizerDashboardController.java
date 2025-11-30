package com.eventmanagement.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


import com.eventmanagement.services.*;
import com.eventmanagement.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalTime;

public class OrganizerDashboardController {
    private static final Logger logger = LoggerFactory.getLogger(OrganizerDashboardController.class);

    @FXML
    private Label userInfoLabel;

    @FXML
    private StackPane contentArea;

    private EventService eventService;
    private TicketService ticketService;
    private String organizerId;

    @FXML
    public void initialize() {
        eventService = EventService.getInstance();
        ticketService = TicketService.getInstance();

        // This should be set from login controller
        userInfoLabel.setText("Event Organizer");
        showCreateEvent();
    }

    @FXML
    private void showCreateEvent() {
        VBox content = createEventCreationPanel();
        contentArea.getChildren().clear();
        contentArea.getChildren().add(content);
    }

    @FXML
    private void showManageEvents() {
        VBox content = createManageEventsPanel();
        contentArea.getChildren().clear();
        contentArea.getChildren().add(content);
    }

    @FXML
    private void showTicketManagement() {
        VBox content = createTicketManagementPanel();
        contentArea.getChildren().clear();
        contentArea.getChildren().add(content);
    }

    @FXML
    private void showAttendeeCommunication() {
        VBox content = createAttendeeCommunicationPanel();
        contentArea.getChildren().clear();
        contentArea.getChildren().add(content);
    }

    @FXML
    private void showReports() {
        VBox content = createReportsPanel();
        contentArea.getChildren().clear();
        contentArea.getChildren().add(content);
    }

    @FXML
    private void handleLogout() {
        // Handle logout
    }

    private VBox createEventCreationPanel() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(20));

        Label titleLabel = new Label("Create New Event");
        titleLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(15);
        form.setPadding(new Insets(20));

        TextField titleField = new TextField();
        titleField.setPromptText("Event Title");
        titleField.setPrefWidth(300);

        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Event Description");
        descriptionArea.setPrefRowCount(4);
        descriptionArea.setWrapText(true);

        DatePicker eventDatePicker = new DatePicker();
        Spinner<Integer> hourSpinner = new Spinner<>(0, 23, 9);
        Spinner<Integer> minuteSpinner = new Spinner<>(0, 59, 0);

        TextField venueField = new TextField();
        venueField.setPromptText("Venue");
        venueField.setPrefWidth(300);

        Spinner<Integer> capacitySpinner = new Spinner<>(10, 10000, 100);

        form.add(new Label("Title:"), 0, 0);
        form.add(titleField, 1, 0);
        form.add(new Label("Description:"), 0, 1);
        form.add(descriptionArea, 1, 1);
        form.add(new Label("Date:"), 0, 2);
        form.add(eventDatePicker, 1, 2);
        form.add(new Label("Time:"), 0, 3);
        HBox timeBox = new HBox(5);
        timeBox.getChildren().addAll(hourSpinner, new Label(":"), minuteSpinner);
        form.add(timeBox, 1, 3);
        form.add(new Label("Venue:"), 0, 4);
        form.add(venueField, 1, 4);
        form.add(new Label("Capacity:"), 0, 5);
        form.add(capacitySpinner, 1, 5);

        Button createBtn = new Button("Create Event");
        createBtn.setStyle("-fx-padding: 10; -fx-background-color: #388e3c; -fx-text-fill: white;");
        createBtn.setOnAction(e -> {
            if (titleField.getText().isEmpty() || venueField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Validation Error");
                alert.setHeaderText("Please fill in all required fields");
                alert.showAndWait();
                return;
            }

            Event event = new Event();
            event.setTitle(titleField.getText());
            event.setDescription(descriptionArea.getText());
            event.setEventDate(eventDatePicker.getValue());
            event.setEventTime(LocalTime.of(hourSpinner.getValue(), minuteSpinner.getValue()));
            event.setVenue(venueField.getText());
            event.setCapacity(capacitySpinner.getValue());
            event.setOrganizerId(organizerId);

            Event createdEvent = eventService.createEvent(event);
            if (createdEvent != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Event created successfully!");
                alert.setContentText("Your event is pending admin approval.");
                alert.showAndWait();
                clearForm(titleField, venueField);
            }
        });

        panel.getChildren().addAll(titleLabel, form, createBtn);
        return panel;
    }

    private VBox createManageEventsPanel() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(20));

        Label titleLabel = new Label("Manage My Events");
        titleLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        panel.getChildren().add(titleLabel);
        return panel;
    }

    private VBox createTicketManagementPanel() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(20));

        Label titleLabel = new Label("Ticket Management");
        titleLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        panel.getChildren().add(titleLabel);
        return panel;
    }

    private VBox createAttendeeCommunicationPanel() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(20));

        Label titleLabel = new Label("Attendee Communication");
        titleLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        panel.getChildren().add(titleLabel);
        return panel;
    }

    private VBox createReportsPanel() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(20));

        Label titleLabel = new Label("My Reports");
        titleLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        panel.getChildren().add(titleLabel);
        return panel;
    }

    private void clearForm(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }
}
