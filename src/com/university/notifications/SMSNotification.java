package com.university.notifications;
public class SMSNotification extends Notification {
    public SMSNotification(String notificationId, String recipient, String subject, String message, String timestamp) {
        super(notificationId, recipient, subject, message, timestamp);
    }
    public boolean send() {
        System.out.println("\n[📱 SMS SENT]");
        System.out.println("To: " + getRecipient());
        System.out.println("Message: " + formatMessage());
        return true;
    }
    public String getNotificationType() {
        return "SMS";
    }
    public String formatMessage() {
        return getSubject() + ": " + getMessage();
    }
}