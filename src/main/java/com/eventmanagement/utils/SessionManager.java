package com.eventmanagement.utils;

import com.eventmanagement.models.User;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private static SessionManager instance;
    private Map<String, User> sessions = new ConcurrentHashMap<>();
    private static final long SESSION_TIMEOUT = 30 * 60 * 1000; // 30 minutes

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    // FIXED: Correct method signature
    public void createSession(String sessionId, User user) {
        sessions.put(sessionId, user);
        System.out.println("Session created: " + sessionId);
    }

    public User getSessionUser(String sessionId) {
        return sessions.get(sessionId);
    }

    public boolean isSessionValid(String sessionId) {
        return sessions.containsKey(sessionId);
    }

    public void invalidateSession(String sessionId) {
        sessions.remove(sessionId);
    }
}
