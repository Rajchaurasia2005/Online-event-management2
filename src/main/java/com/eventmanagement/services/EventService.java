package com.eventmanagement.services;

import com.eventmanagement.models.Event;
import com.eventmanagement.dao.EventDAO;
import com.eventmanagement.utils.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class EventService {
    private static final Logger logger = LoggerFactory.getLogger(EventService.class);
    private static EventService instance;
    private EventDAO eventDAO;

    private EventService() {
        this.eventDAO = new EventDAO();
    }

    public static synchronized EventService getInstance() {
        if (instance == null) {
            instance = new EventService();
        }
        return instance;
    }

    public Event createEvent(Event event) {
        if (event == null || !isValidEvent(event)) {
            logger.warn("Invalid event data for creation");
            return null;
        }

        if (eventDAO.createEvent(event)) {
            logger.info("Event created successfully: {}", event.getEventId());
            return event;
        }
        return null;
    }

    public Event getEventById(String eventId) {
        return eventDAO.getEventById(eventId);
    }

    public List<Event> getEventsByOrganizer(String organizerId) {
        return eventDAO.getEventsByOrganizer(organizerId);
    }

    public List<Event> getPendingEvents() {
        return eventDAO.getPendingEvents();
    }

    public List<Event> getApprovedEvents() {
        return eventDAO.getApprovedEvents();
    }

    public boolean approveEvent(String eventId) {
        Event event = eventDAO.getEventById(eventId);
        if (event == null) {
            logger.warn("Event not found for approval: {}", eventId);
            return false;
        }
        return eventDAO.approveEvent(eventId);
    }

    public boolean rejectEvent(String eventId, String reason) {
        if (eventId == null || reason == null || reason.isEmpty()) {
            logger.warn("Invalid parameters for event rejection");
            return false;
        }
        return eventDAO.rejectEvent(eventId, reason);
    }

    public boolean updateEvent(Event event) {
        if (event == null || !isValidEvent(event)) {
            logger.warn("Invalid event data for update");
            return false;
        }
        return eventDAO.updateEvent(event);
    }

    private boolean isValidEvent(Event event) {
        return event != null &&
                ValidationUtil.isValidEventTitle(event.getTitle()) &&
                event.getEventDate() != null &&
                event.getEventTime() != null &&
                event.getVenue() != null && !event.getVenue().isEmpty() &&
                event.getCapacity() > 0;
    }
}
