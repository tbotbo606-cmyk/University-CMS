package com.university.services;

import com.university.models.Course;
import com.university.models.Enrollment;
import com.university.models.Student;
import com.university.models.User;
import com.university.repositories.DataRepository;
import com.university.repositories.UniversityDataRepository;

import java.util.UUID;

public class EnrollmentManager implements EnrollmentService {

    private final DataRepository repository;
    private static final int MAX_CREDITS = 18;

    public EnrollmentManager() {
        this.repository = new UniversityDataRepository();
    }
    public Enrollment enrollStudent(String studentId, String courseCode, String semester) {

        User user = repository.getUserById(studentId);
        if (user == null || !(user instanceof Student)) {
            System.out.println("Error: Student not found: " + studentId);
            return null;
        }

        Course course = repository.getCourseByCode(courseCode);
        if (course == null) {
            System.out.println("Error: Course not found: " + courseCode);
            return null;
        }

        if (course.isFull()) {
            System.out.println("Error: Course is full.");
            return null;
        }

        Enrollment[] currentEnrollments = getStudentEnrollments(studentId);
        for (Enrollment e : currentEnrollments) {
            if (e.getCourseCode().equalsIgnoreCase(courseCode) && e.getSemester().equalsIgnoreCase(semester)) {
                System.out.println("Error: Already enrolled in " + courseCode);
                return null;
            }
        }

        if (!checkPrerequisites(studentId, courseCode)) {
            System.out.println("Error: Prerequisites not met for " + courseCode);
            return null;
        }

        if (!checkCreditLimit(studentId, course.getCredits())) {
            System.out.println("Error: Credit limit exceeded (" + MAX_CREDITS + ")");
            return null;
        }

        String enrollmentId = "ENR-" + UUID.randomUUID().toString().substring(0, 8);
        Enrollment newEnrollment = new Enrollment(enrollmentId, studentId, courseCode, semester);

        if (repository.saveEnrollment(newEnrollment)) {
            course.addStudent(studentId);
            repository.updateCourse(course);
            System.out.println("Success: Enrolled in " + courseCode);
            return newEnrollment;
        }

        return null;
    }
    public boolean dropCourse(String studentId, String courseCode) {
        Enrollment[] enrollments = getStudentEnrollments(studentId);

        for (Enrollment e : enrollments) {
            if (e.getCourseCode().equalsIgnoreCase(courseCode)) {
                boolean deleted = repository.deleteEnrollment(e.getEnrollmentId());

                if (deleted) {
                    Course course = repository.getCourseByCode(courseCode);
                    if (course != null) {
                        course.removeStudent(studentId);
                        repository.updateCourse(course);
                    }
                    System.out.println("Success: Dropped course " + courseCode);
                    return true;
                }
            }
        }
        System.out.println("Error: Enrollment not found to drop.");
        return false;
    }
    public boolean checkPrerequisites(String studentId, String courseCode) {
        User user = repository.getUserById(studentId);
        Course course = repository.getCourseByCode(courseCode);

        if (!(user instanceof Student) || course == null) return false;

        Student student = (Student) user;
        for (String requiredCourseCode : course.getPrerequisites()) {
            if (!student.hasCompletedCourse(requiredCourseCode)) {
                System.out.println("Missing Prerequisite: " + requiredCourseCode);
                return false;
            }
        }
        return true;
    }
    public boolean checkCreditLimit(String studentId, int additionalCredits) {
        Enrollment[] currentEnrollments = getStudentEnrollments(studentId);
        int currentCredits = 0;
        for (Enrollment e : currentEnrollments) {
            Course c = repository.getCourseByCode(e.getCourseCode());
            if (c != null) {
                currentCredits += c.getCredits();
            }
        }
        return (currentCredits + additionalCredits) <= MAX_CREDITS;
    }
    public Enrollment[] getStudentEnrollments(String studentId) {
        return repository.getEnrollmentsByStudent(studentId);
    }
    public Enrollment[] getCourseEnrollments(String courseCode) {
        return repository.getEnrollmentsByCourse(courseCode);
    }
}