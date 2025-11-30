package com.eventmanagement.utils;

import com.eventmanagement.config.DatabaseConfig;
import com.eventmanagement.dao.UserDAO;
import com.eventmanagement.models.User;
import com.eventmanagement.utils.PasswordUtil;

public class InitialDataLoader {
    public static void loadDemoUsers() {
        UserDAO userDAO = new UserDAO();

        // Check if admin exists
        if (userDAO.getUserByUsername("admin") == null) {
            System.out.println("Creating demo users...");

            // Admin
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(PasswordUtil.hashPassword("admin123"));
            admin.setEmail("admin@eventmanagement.com");
            admin.setFullName("System Administrator");
            admin.setRole(User.UserRole.ADMIN);
            userDAO.createUser(admin);

            // Organizer
            User organizer = new User();
            organizer.setUsername("organizer1");
            organizer.setPassword(PasswordUtil.hashPassword("org123456"));
            organizer.setEmail("organizer@eventmanagement.com");
            organizer.setFullName("John Organizer");
            organizer.setRole(User.UserRole.ORGANIZER);
            userDAO.createUser(organizer);

            // Attendee
            User attendee = new User();
            attendee.setUsername("attendee1");
            attendee.setPassword(PasswordUtil.hashPassword("att123456"));
            attendee.setEmail("attendee@eventmanagement.com");
            attendee.setFullName("Jane Attendee");
            attendee.setRole(User.UserRole.ATTENDEE);
            userDAO.createUser(attendee);

            System.out.println("✅ Demo users created successfully!");
            System.out.println("👤 Demo credentials:");
            System.out.println("   Admin: admin / admin123");
            System.out.println("   Organizer: organizer1 / org123456");
            System.out.println("   Attendee: attendee1 / att123456");
        } else {
            System.out.println("Demo users already exist.");
        }
    }
}
