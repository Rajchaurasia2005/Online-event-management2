package com.eventmanagement.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Ticket implements Serializable {
    private static final long serialVersionUID = 1L;

    private String ticketId;
    private String eventId;
    private double price;
    private int totalQuantity;
    private int soldQuantity;
    private String ticketType;
    private LocalDateTime createdDate;

    public Ticket() {
        this.ticketId = java.util.UUID.randomUUID().toString();
        this.createdDate = LocalDateTime.now();
        this.soldQuantity = 0;
    }

    // Getters and Setters
    public String getTicketId() { return ticketId; }
    public void setTicketId(String ticketId) { this.ticketId = ticketId; }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getTotalQuantity() { return totalQuantity; }
    public void setTotalQuantity(int totalQuantity) { this.totalQuantity = totalQuantity; }

    public int getSoldQuantity() { return soldQuantity; }
    public void setSoldQuantity(int soldQuantity) { this.soldQuantity = soldQuantity; }

    public String getTicketType() { return ticketType; }
    public void setTicketType(String ticketType) { this.ticketType = ticketType; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public int getAvailableQuantity() {
        return totalQuantity - soldQuantity;
    }
}