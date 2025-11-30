package com.eventmanagement.services;

import com.eventmanagement.models.Ticket;
import com.eventmanagement.models.Event;
import com.eventmanagement.dao.TicketDAO;
import com.eventmanagement.dao.EventDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class TicketService {
    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);
    private static TicketService instance;
    private TicketDAO ticketDAO;
    private EventDAO eventDAO;

    private TicketService() {
        this.ticketDAO = new TicketDAO();
        this.eventDAO = new EventDAO();
    }

    public static synchronized TicketService getInstance() {
        if (instance == null) {
            instance = new TicketService();
        }
        return instance;
    }

    public Ticket createTicket(Ticket ticket) {
        if (ticket == null || !isValidTicket(ticket)) {
            logger.warn("Invalid ticket data for creation");
            return null;
        }

        Event event = eventDAO.getEventById(ticket.getEventId());
        if (event == null) {
            logger.warn("Event not found for ticket creation: {}", ticket.getEventId());
            return null;
        }

        if (ticketDAO.createTicket(ticket)) {
            logger.info("Ticket created successfully: {}", ticket.getTicketId());
            return ticket;
        }
        return null;
    }

    public List<Ticket> getTicketsByEvent(String eventId) {
        return ticketDAO.getTicketsByEvent(eventId);
    }

    public Ticket getTicketById(String ticketId) {
        return ticketDAO.getTicketById(ticketId);
    }

    public boolean purchaseTickets(String ticketId, int quantity) {
        Ticket ticket = ticketDAO.getTicketById(ticketId);
        if (ticket == null) {
            logger.warn("Ticket not found: {}", ticketId);
            return false;
        }

        if (ticket.getAvailableQuantity() < quantity) {
            logger.warn("Insufficient ticket quantity available");
            return false;
        }

        int newSoldQuantity = ticket.getSoldQuantity() + quantity;
        return ticketDAO.updateTicketQuantity(ticketId, newSoldQuantity);
    }

    private boolean isValidTicket(Ticket ticket) {
        return ticket != null &&
                ticket.getPrice() > 0 &&
                ticket.getTotalQuantity() > 0 &&
                ticket.getTicketType() != null && !ticket.getTicketType().isEmpty();
    }
}

