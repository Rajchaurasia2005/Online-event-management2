package com.eventmanagement.services;

import com.eventmanagement.dao.UserDAO;
import com.eventmanagement.models.User;
import java.util.List;

public class UserService {
    private static UserService instance;  // ✅ Singleton
    private UserDAO userDAO;

    private UserService() {  // ✅ Private constructor
        userDAO = new UserDAO();
    }

    public static UserService getInstance() {  // ✅ MISSING METHOD ADDED
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    public List<User> getUsersByRole(User.UserRole role) {
        return userDAO.getUsersByRole(role);
    }

    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }

    public boolean deleteUser(int userId) {
        return userDAO.deleteUser(userId);
    }

    public boolean updatePassword(int userId, String newHash) {
        return userDAO.updatePassword(userId, newHash);
    }
}
