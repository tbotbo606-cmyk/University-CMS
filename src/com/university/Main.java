package com.university;

import com.university.models.*;
import com.university.services.*;
import com.university.repositories.UniversityDataRepository;
import com.university.notifications.NotificationManager;
import com.university.reports.Report;
import com.university.reports.ReportManager;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static UserService userService = new UserService();
    private static CourseService courseService = new CourseService();
    private static EnrollmentManager enrollmentManager = new EnrollmentManager();
    private static GradeService gradeService = new GradeService();
    private static ReportManager reportManager = new ReportManager();
    private static NotificationManager notificationManager = new NotificationManager();
    private static UniversityDataRepository repository = new UniversityDataRepository(); // للوصول المباشر للبيانات
    private static User currentUser = null;
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("University Course Management System");
        System.out.println("========================================\n");
        boolean running = true;
        while (running) {
            if (currentUser == null) {
                showLoginMenu();
            } else {
                String role = currentUser.getRole();
                System.out.println("\nLogged in as: " + currentUser.getFullName() + " [" + role + "]");
                switch (role) {
                    case "ADMIN":
                        showAdminMenu();
                        break;
                    case "INSTRUCTOR":
                        showInstructorMenu();
                        break;
                    case "STUDENT":
                        showStudentMenu();
                        break;
                    default:
                        System.out.println("Unknown role. Logging out.");
                        logout();
                }
            }
        }

        scanner.close();
    }

    private static void logout() {
        currentUser = null;
        System.out.println("Logged out successfully.");
    }
    private static void showLoginMenu() {
        System.out.println("\n=== Login Menu ===");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print("Choose an option: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                performLogin();
                break;
            case 2:
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }
    private static void performLogin() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = userService.login(username, password);

        if (user != null) {
            currentUser = user;
            System.out.println("Login Successful!");
        } else {
            System.out.println("Invalid credentials.");
        }
    }
    private static void showAdminMenu() {
        System.out.println("\n=== Admin Menu ===");
        System.out.println("1. Add New User (Student)");
        System.out.println("2. Add New Course");
        System.out.println("3. View All Courses");
        System.out.println("4. Generate Roster Report");
        System.out.println("5. Send Announcements");
        System.out.println("6. Logout");
        System.out.print("Choose an option: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                System.out.print("Enter Student ID: ");
                String sId = scanner.nextLine();
                System.out.print("Enter Name: ");
                String sName = scanner.nextLine();
                System.out.print("Enter Email: ");
                String sEmail = scanner.nextLine();
                Student newStudent = new Student(sId, sName.toLowerCase(), "pass123", sEmail, sName, sId, "CS", 1);
                if (repository.saveUser(newStudent)) {
                    System.out.println("Student created successfully. Password is 'pass123'");
                }
                break;
            case 2:
                System.out.print("Course Code: ");
                String code = scanner.nextLine();
                System.out.print("Title: ");
                String title = scanner.nextLine();
                System.out.print("Credits: ");
                int credits = getIntInput();
                courseService.createCourse(code, title, credits, "INS-001", 30);
                break;
            case 3:
                Course[] courses = courseService.listAllCourses();
                for (Course c : courses) System.out.println(c);
                break;
            case 4:
                System.out.print("Enter Course Code for Roster: ");
                String rCode = scanner.nextLine();
                Report roster = reportManager.generateCourseRoster(rCode);
                if (roster != null) System.out.println(roster.generateReport());
                break;
            case 5:
                System.out.print("Enter Announcement Message: ");
                String msg = scanner.nextLine();
                String[] fakeIds = {"ST-001", "ST-002"};
                notificationManager.sendAnnouncement(fakeIds, "Admin Announcement", msg);
                break;
            case 6:
                logout();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }
    private static void showInstructorMenu() {
        System.out.println("\n=== Instructor Menu ===");
        System.out.println("1. View Courses");
        System.out.println("2. View Course Roster");
        System.out.println("3. Assign Grades");
        System.out.println("4. Logout");
        System.out.print("Choose an option: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                Course[] courses = courseService.listAllCourses();
                for (Course c : courses) System.out.println(c);
                break;
            case 2:
                System.out.print("Enter Course Code: ");
                String code = scanner.nextLine();
                Report roster = reportManager.generateCourseRoster(code);
                if (roster != null) System.out.println(roster.generateReport());
                break;
            case 3:
                System.out.print("Student ID: ");
                String sId = scanner.nextLine();
                System.out.print("Course Code: ");
                String cCode = scanner.nextLine();
                System.out.print("Score (0-100): ");
                double score = Double.parseDouble(scanner.nextLine());

                if (gradeService.assignGrade(sId, cCode, score, "Fall 2025")) {
                    notificationManager.notifyGradePosted(sId, cCode, score >= 60 ? "Pass" : "Fail");
                }
                break;
            case 4:
                logout();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }
    private static void showStudentMenu() {
        if (!(currentUser instanceof Student)) {
            System.out.println("Error: Current user is not a Student object.");
            logout();
            return;
        }

        Student currentStudent = (Student) currentUser;
        String studentId = currentStudent.getStudentId();

        System.out.println("\n=== Student Menu ===");
        System.out.println("1. View Available Courses");
        System.out.println("2. Enroll in Course");
        System.out.println("3. Drop Course");
        System.out.println("4. View My Enrollments");
        System.out.println("5. View My Grades");
        System.out.println("6. Generate Transcript");
        System.out.println("7. Logout");
        System.out.print("Choose an option: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                Course[] courses = courseService.listAllCourses();
                for (Course c : courses) System.out.println(c);
                break;
            case 2:
                System.out.print("Enter Course Code to Enroll: ");
                String code = scanner.nextLine();
                if (enrollmentManager.enrollStudent(studentId, code, "Fall 2025") != null) {
                    notificationManager.notifyEnrollment(studentId, code);
                }
                break;
            case 3:
                System.out.print("Enter Course Code to Drop: ");
                String dropCode = scanner.nextLine();
                if (enrollmentManager.dropCourse(studentId, dropCode)) {
                    notificationManager.notifyCourseDropped(studentId, dropCode);
                }
                break;
            case 4:
                Enrollment[] enrollments = enrollmentManager.getStudentEnrollments(studentId);
                for (Enrollment e : enrollments) System.out.println(e);
                break;
            case 5:
                gradeService.viewStudentGrades(studentId);
                break;
            case 6:
                Report transcript = reportManager.generateTranscript(studentId);
                if (transcript != null) System.out.println(transcript.generateReport());
                break;
            case 7:
                logout();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }
    private static int getIntInput() {
        try {
            String line = scanner.nextLine();
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}