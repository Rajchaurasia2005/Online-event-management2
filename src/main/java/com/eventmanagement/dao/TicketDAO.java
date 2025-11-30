package com.eventmanagement.dao;

import com.eventmanagement.models.Ticket;
import java.sql.*;
import java.util.*;

public class TicketDAO extends BaseDAO {

    public boolean createTicket(Ticket ticket) {
        String sql = "INSERT INTO tickets (ticketId, eventId, price, totalQuantity, ticketType, createdDate) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ticket.getTicketId());
            pstmt.setString(2, ticket.getEventId());
            pstmt.setDouble(3, ticket.getPrice());
            pstmt.setInt(4, ticket.getTotalQuantity());
            pstmt.setString(5, ticket.getTicketType());
            pstmt.setTimestamp(6, Timestamp.valueOf(ticket.getCreatedDate()));

            pstmt.executeUpdate();
            logger.info("Ticket created: {}", ticket.getTicketId());
            return true;
        } catch (SQLException e) {
            logger.error("Error creating ticket", e);
            return false;
        }
    }

    public Ticket getTicketById(String ticketId) {
        String sql = "SELECT * FROM tickets WHERE ticketId = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ticketId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToTicket(rs);
            }
        } catch (SQLException e) {
            logger.error("Error getting ticket by ID", e);
        }
        return null;
    }

    public List<Ticket> getTicketsByEvent(String eventId) {
        String sql = "SELECT * FROM tickets WHERE eventId = ?";
        List<Ticket> tickets = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, eventId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                tickets.add(mapResultSetToTicket(rs));
            }
        } catch (SQLException e) {
            logger.error("Error getting tickets by event", e);
        }
        return tickets;
    }

    public boolean updateTicketQuantity(String ticketId, int soldQuantity) {
        String sql = "UPDATE tickets SET soldQuantity = ? WHERE ticketId = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, soldQuantity);
            pstmt.setString(2, ticketId);

            pstmt.executeUpdate();
            logger.info("Ticket quantity updated: {}", ticketId);
            return true;
        } catch (SQLException e) {
            logger.error("Error updating ticket quantity", e);
            return false;
        }
    }

    private Ticket mapResultSetToTicket(ResultSet rs) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setTicketId(rs.getString("ticketId"));
        ticket.setEventId(rs.getString("eventId"));
        ticket.setPrice(rs.getDouble("price"));
        ticket.setTotalQuantity(rs.getInt("totalQuantity"));
        ticket.setSoldQuantity(rs.getInt("soldQuantity"));
        ticket.setTicketType(rs.getString("ticketType"));

        Timestamp createdDate = rs.getTimestamp("createdDate");
        if (createdDate != null) {
            ticket.setCreatedDate(createdDate.toLocalDateTime());
        }

        return ticket;
    }
}