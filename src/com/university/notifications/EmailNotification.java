package com.university.notifications;
public class EmailNotification extends Notification {
    public EmailNotification(String notificationId, String recipient, String subject, String message, String timestamp) {
        super(notificationId, recipient, subject, message, timestamp);
    }
    public boolean send() {
        System.out.println("\n[ EMAIL SENT]");
        System.out.println("To: " + getRecipient());
        System.out.println("Subject: " + getSubject());
        System.out.println("Body: \n" + formatMessage());
        return true;
    }
    public String getNotificationType() {
        return "EMAIL";
    }
    public String formatMessage() {
        return "Dear User,\n\n" + getMessage() + "\n\nBest Regards,\nUniversity Admin";
    }
}