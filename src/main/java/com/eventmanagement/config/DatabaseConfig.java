package com.eventmanagement.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;

public class DatabaseConfig {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);
    private static DatabaseConfig instance;
    private Connection connection;

    // YOUR MYSQL CONNECTION STRING
    private static final String DB_URL =
            "jdbc:mysql://mysql-134b9aef-developapp007-06b2.g.aivencloud.com:23811/defaultdb" +
                    "?ssl-mode=REQUIRED&user=" + "avnadmin" +
                    "&password=" + "AVNS_asO8fMaW_peZVuwn6T1";

    private DatabaseConfig() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            createTables();
            System.out.println("MySQL database connected successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }

//    private void createTables() throws SQLException {
//        try (Statement stmt = connection.createStatement()) {
//            // Users table
//            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
//                    "userId TEXT PRIMARY KEY," +
//                    "username TEXT UNIQUE NOT NULL," +
//                    "password TEXT NOT NULL," +
//                    "email TEXT UNIQUE NOT NULL," +
//                    "fullName TEXT NOT NULL," +
//                    "role TEXT NOT NULL," +
//                    "isActive INTEGER DEFAULT 1," +
//                    "createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
//                    "lastModified TIMESTAMP)");
//
//            // Events table
//            stmt.execute("CREATE TABLE IF NOT EXISTS events (" +
//                    "eventId TEXT PRIMARY KEY," +
//                    "organizerId TEXT NOT NULL," +
//                    "title TEXT NOT NULL," +
//                    "description TEXT," +
//                    "eventDate DATE NOT NULL," +
//                    "eventTime TIME NOT NULL," +
//                    "venue TEXT NOT NULL," +
//                    "capacity INTEGER NOT NULL," +
//                    "registeredCount INTEGER DEFAULT 0," +
//                    "approvalStatus TEXT DEFAULT 'PENDING'," +
//                    "rejectionReason TEXT," +
//                    "isActive INTEGER DEFAULT 1," +
//                    "createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
//                    "lastModified TIMESTAMP," +
//                    "FOREIGN KEY(organizerId) REFERENCES users(userId))");
//
//            // Tickets table
//            stmt.execute("CREATE TABLE IF NOT EXISTS tickets (" +
//                    "ticketId TEXT PRIMARY KEY," +
//                    "eventId TEXT NOT NULL," +
//                    "price REAL NOT NULL," +
//                    "totalQuantity INTEGER NOT NULL," +
//                    "soldQuantity INTEGER DEFAULT 0," +
//                    "ticketType TEXT," +
//                    "createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
//                    "FOREIGN KEY(eventId) REFERENCES events(eventId))");
//
//            // Registrations table
//            stmt.execute("CREATE TABLE IF NOT EXISTS registrations (" +
//                    "registrationId TEXT PRIMARY KEY," +
//                    "userId TEXT NOT NULL," +
//                    "eventId TEXT NOT NULL," +
//                    "ticketId TEXT NOT NULL," +
//                    "quantity INTEGER NOT NULL," +
//                    "totalAmount REAL NOT NULL," +
//                    "paymentStatus TEXT DEFAULT 'PENDING'," +
//                    "registrationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
//                    "transactionId TEXT," +
//                    "FOREIGN KEY(userId) REFERENCES users(userId)," +
//                    "FOREIGN KEY(eventId) REFERENCES events(eventId)," +
//                    "FOREIGN KEY(ticketId) REFERENCES tickets(ticketId))");
//
//            // Messages table
//            stmt.execute("CREATE TABLE IF NOT EXISTS messages (" +
//                    "messageId TEXT PRIMARY KEY," +
//                    "senderId TEXT NOT NULL," +
//                    "recipientId TEXT NOT NULL," +
//                    "eventId TEXT," +
//                    "subject TEXT NOT NULL," +
//                    "content TEXT NOT NULL," +
//                    "isRead INTEGER DEFAULT 0," +
//                    "sentDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
//                    "FOREIGN KEY(senderId) REFERENCES users(userId)," +
//                    "FOREIGN KEY(recipientId) REFERENCES users(userId)," +
//                    "FOREIGN KEY(eventId) REFERENCES events(eventId))");
//        }
//    }

    private void createTables() throws SQLException {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users ("+
            "user_id INT PRIMARY KEY AUTO_INCREMENT,"+
            "username VARCHAR(50) UNIQUE NOT NULL,"+
           "password_hash VARCHAR(255) NOT NULL,"+
            "email VARCHAR(100) UNIQUE NOT NULL,"+
            "full_name VARCHAR(100) NOT NULL,"+
            "role ENUM('ADMIN', 'ORGANIZER', 'ATTENDEE') NOT NULL,"+
            "is_active BOOLEAN DEFAULT TRUE,"+
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createUsersTable);
            System.out.println("Users table created/verified in MySQL");
        }
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL);
            }
        } catch (SQLException e) {
            logger.error("Error getting database connection", e);
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Error closing database connection", e);
        }
    }
}