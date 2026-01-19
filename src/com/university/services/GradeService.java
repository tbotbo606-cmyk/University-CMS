package com.university.services;

import com.university.models.Enrollment;
import com.university.models.Grade;
import com.university.models.GradeCalculator;
import com.university.models.LetterGradeCalculator;
import com.university.repositories.DataRepository;
import com.university.repositories.UniversityDataRepository;

import java.util.UUID;

public class GradeService {
    private final DataRepository repository;

    public GradeService() {
        this.repository = new UniversityDataRepository();
    }
    public boolean assignGrade(String studentId, String courseCode, double score, String semester) {

        Enrollment[] enrollments = repository.getEnrollmentsByStudent(studentId);
        boolean isEnrolled = false;

        for (Enrollment e : enrollments) {
            if (e.getCourseCode().equalsIgnoreCase(courseCode)) {
                isEnrolled = true;
                break;
            }
        }

        if (!isEnrolled) {
            System.out.println("Error: Student is not enrolled in this course.");
            return false;
        }

        GradeCalculator calculator = new LetterGradeCalculator(courseCode, studentId);

        if (!calculator.isValidScore(score)) {
            System.out.println("Error: Invalid score. Must be between 0-100.");
            return false;
        }

        String letterGrade = calculator.calculateGrade(score);

        String gradeId = "GR-" + UUID.randomUUID().toString().substring(0, 8);
        Grade newGrade = new Grade(gradeId, studentId, courseCode, score, letterGrade, semester);

        if (repository.saveGrade(newGrade)) {
            System.out.println("Success: Grade assigned: " + letterGrade + " (" + score + ")");
            return true;
        } else {
            System.out.println("Error: Failed to save grade.");
            return false;
        }
    }

    public void viewStudentGrades(String studentId) {
        Grade[] grades = repository.getGradesByStudent(studentId);
        if (grades.length == 0) {
            System.out.println("No grades found for student: " + studentId);
            return;
        }

        System.out.println("\n--- Grades for Student: " + studentId + " ---");
        for (Grade g : grades) {
            System.out.println("Course: " + g.getCourseCode() +
                    " | Score: " + g.getScore() +
                    " | Grade: " + g.getLetterGrade());
        }
    }
}