package com.eventmanagement.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.eventmanagement.config.DatabaseConfig;
import com.eventmanagement.utils.InitialDataLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventManagementApp extends Application {
    private static final Logger logger = LoggerFactory.getLogger(EventManagementApp.class);

    @Override
    public void start(Stage primaryStage) {
        try {
            // Initialize database
            DatabaseConfig.getInstance();
            InitialDataLoader.loadDemoUsers();



            // Load login FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);

            primaryStage.setTitle("Event Management System");
            primaryStage.setScene(scene);
            primaryStage.show();

            logger.info("Application started successfully");
        } catch (Exception e) {
            logger.error("Error starting application", e);
        }
    }

    @Override
    public void stop() {
        DatabaseConfig.getInstance().closeConnection();
        logger.info("Application closed");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
