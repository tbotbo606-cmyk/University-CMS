package com.university.notifications;

import com.university.models.User;
import com.university.repositories.DataRepository;
import com.university.repositories.UniversityDataRepository;

import java.util.Date;
import java.util.UUID;

public class NotificationManager implements NotificationService {

    private final DataRepository repository;

    public NotificationManager() {
        this.repository = new UniversityDataRepository();
    }

    public boolean sendNotification(Notification notification) {
        return notification.send();
    }
    public void notifyEnrollment(String studentId, String courseCode) {
        User student = repository.getUserById(studentId);
        if (student != null) {
            String id = generateId();
            String time = new Date().toString();
            String subject = "Enrollment Confirmation";
            String message = "You have successfully enrolled in course: " + courseCode;
            Notification n = new EmailNotification(id, student.getEmail(), subject, message, time);
            sendNotification(n);
        }
    }

    public void notifyGradePosted(String studentId, String courseCode, String grade) {
        User student = repository.getUserById(studentId);
        if (student != null) {
            String id = generateId();
            String time = new Date().toString();
            String subject = "New Grade Posted";
            String message = "Your grade for " + courseCode + " has been posted. Grade: " + grade;

            Notification n = new SMSNotification(id, student.getEmail(), subject, message, time);
            sendNotification(n);
        }
    }
    public void notifyCourseDropped(String studentId, String courseCode) {
        User student = repository.getUserById(studentId);
        if (student != null) {
            String id = generateId();
            String time = new Date().toString();
            String subject = "Course Dropped";
            String message = "You have dropped the course: " + courseCode;

            Notification n = new EmailNotification(id, student.getEmail(), subject, message, time);
            sendNotification(n);
        }
    }
    public void sendAnnouncement(String[] recipientIds, String subject, String message) {
        String time = new Date().toString();
        System.out.println("\n=== Sending Bulk Announcement ===");

        for (String userId : recipientIds) {
            User user = repository.getUserById(userId);
            if (user != null) {
                String id = generateId();
                Notification n = new EmailNotification(id, user.getEmail(), subject, message, time);
                sendNotification(n);
            }
        }
    }

    private String generateId() {
        return "NOTIF-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
