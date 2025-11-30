package com.eventmanagement.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Registration implements Serializable {
    private static final long serialVersionUID = 1L;

    private String registrationId;
    private String userId;
    private String eventId;
    private String ticketId;
    private int quantity;
    private double totalAmount;
    private PaymentStatus paymentStatus;
    private LocalDateTime registrationDate;
    private String transactionId;

    public enum PaymentStatus {
        PENDING, COMPLETED, FAILED, CANCELLED
    }

    public Registration() {
        this.registrationId = java.util.UUID.randomUUID().toString();
        this.registrationDate = LocalDateTime.now();
        this.paymentStatus = PaymentStatus.PENDING;
    }

    // Getters and Setters
    public String getRegistrationId() { return registrationId; }
    public void setRegistrationId(String registrationId) { this.registrationId = registrationId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getTicketId() { return ticketId; }
    public void setTicketId(String ticketId) { this.ticketId = ticketId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }

    public LocalDateTime getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDateTime registrationDate) { this.registrationDate = registrationDate; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
}