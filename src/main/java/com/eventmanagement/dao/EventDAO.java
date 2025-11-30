package com.eventmanagement.dao;

import com.eventmanagement.models.Event;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class EventDAO extends BaseDAO {

    public boolean createEvent(Event event) {
        String sql = "INSERT INTO events (eventId, organizerId, title, description, eventDate, " +
                "eventTime, venue, capacity, approvalStatus, createdDate) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, event.getEventId());
            pstmt.setString(2, event.getOrganizerId());
            pstmt.setString(3, event.getTitle());
            pstmt.setString(4, event.getDescription());
            pstmt.setDate(5, java.sql.Date.valueOf(event.getEventDate()));
            pstmt.setTime(6, java.sql.Time.valueOf(event.getEventTime()));
            pstmt.setString(7, event.getVenue());
            pstmt.setInt(8, event.getCapacity());
            pstmt.setString(9, event.getApprovalStatus().toString());
            pstmt.setTimestamp(10, Timestamp.valueOf(event.getCreatedDate()));

            pstmt.executeUpdate();
            logger.info("Event created: {}", event.getEventId());
            return true;
        } catch (SQLException e) {
            logger.error("Error creating event", e);
            return false;
        }
    }

    public Event getEventById(String eventId) {
        String sql = "SELECT * FROM events WHERE eventId = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, eventId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToEvent(rs);
            }
        } catch (SQLException e) {
            logger.error("Error getting event by ID", e);
        }
        return null;
    }

    public List<Event> getEventsByOrganizer(String organizerId) {
        String sql = "SELECT * FROM events WHERE organizerId = ? AND isActive = 1";
        List<Event> events = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, organizerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                events.add(mapResultSetToEvent(rs));
            }
        } catch (SQLException e) {
            logger.error("Error getting events by organizer", e);
        }
        return events;
    }

    public List<Event> getPendingEvents() {
        String sql = "SELECT * FROM events WHERE approvalStatus = 'PENDING' AND isActive = 1 " +
                "ORDER BY createdDate DESC";
        List<Event> events = new ArrayList<>();

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                events.add(mapResultSetToEvent(rs));
            }
        } catch (SQLException e) {
            logger.error("Error getting pending events", e);
        }
        return events;
    }

    public List<Event> getApprovedEvents() {
        String sql = "SELECT * FROM events WHERE approvalStatus = 'APPROVED' AND isActive = 1 " +
                "ORDER BY eventDate ASC";
        List<Event> events = new ArrayList<>();

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                events.add(mapResultSetToEvent(rs));
            }
        } catch (SQLException e) {
            logger.error("Error getting approved events", e);
        }
        return events;
    }

    public boolean approveEvent(String eventId) {
        String sql = "UPDATE events SET approvalStatus = 'APPROVED', lastModified = ? " +
                "WHERE eventId = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setString(2, eventId);

            pstmt.executeUpdate();
            logger.info("Event approved: {}", eventId);
            return true;
        } catch (SQLException e) {
            logger.error("Error approving event", e);
            return false;
        }
    }

    public boolean rejectEvent(String eventId, String reason) {
        String sql = "UPDATE events SET approvalStatus = 'REJECTED', rejectionReason = ?, " +
                "lastModified = ? WHERE eventId = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, reason);
            pstmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setString(3, eventId);

            pstmt.executeUpdate();
            logger.info("Event rejected: {}", eventId);
            return true;
        } catch (SQLException e) {
            logger.error("Error rejecting event", e);
            return false;
        }
    }

    public boolean updateEvent(Event event) {
        String sql = "UPDATE events SET title = ?, description = ?, eventDate = ?, " +
                "eventTime = ?, venue = ?, capacity = ?, lastModified = ? WHERE eventId = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, event.getTitle());
            pstmt.setString(2, event.getDescription());
            pstmt.setDate(3, java.sql.Date.valueOf(event.getEventDate()));
            pstmt.setTime(4, java.sql.Time.valueOf(event.getEventTime()));
            pstmt.setString(5, event.getVenue());
            pstmt.setInt(6, event.getCapacity());
            pstmt.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setString(8, event.getEventId());

            pstmt.executeUpdate();
            logger.info("Event updated: {}", event.getEventId());
            return true;
        } catch (SQLException e) {
            logger.error("Error updating event", e);
            return false;
        }
    }

    private Event mapResultSetToEvent(ResultSet rs) throws SQLException {
        Event event = new Event();
        event.setEventId(rs.getString("eventId"));
        event.setOrganizerId(rs.getString("organizerId"));
        event.setTitle(rs.getString("title"));
        event.setDescription(rs.getString("description"));
        event.setEventDate(rs.getDate("eventDate").toLocalDate());
        event.setEventTime(rs.getTime("eventTime").toLocalTime());
        event.setVenue(rs.getString("venue"));
        event.setCapacity(rs.getInt("capacity"));
        event.setRegisteredCount(rs.getInt("registeredCount"));
        event.setApprovalStatus(Event.ApprovalStatus.valueOf(rs.getString("approvalStatus")));
        event.setRejectionReason(rs.getString("rejectionReason"));
        event.setActive(rs.getInt("isActive") == 1);

        Timestamp createdDate = rs.getTimestamp("createdDate");
        if (createdDate != null) {
            event.setCreatedDate(createdDate.toLocalDateTime());
        }

        Timestamp lastModified = rs.getTimestamp("lastModified");
        if (lastModified != null) {
            event.setLastModified(lastModified.toLocalDateTime());
        }

        return event;
    }
}