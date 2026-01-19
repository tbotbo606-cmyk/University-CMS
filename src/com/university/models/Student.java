package com.university.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String studentId;
    private String program;
    private int year;
    private double gpa;
    private List<String> completedCourses;

    public Student(String userId, String username, String password, String email,
                   String fullName, String studentId, String program, int year) {
        super(userId, username, password, email, fullName);
        this.studentId = studentId;
        this.program = program;
        this.year = year;
        this.gpa = 0.0;
        this.completedCourses = new ArrayList<>();
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getProgram() { return program; }
    public void setProgram(String program) { this.program = program; }

    public int getYear() { return year; }
    public void setYear(int year) { if (year >= 1 && year <= 4) this.year = year; }

    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { if (gpa >= 0.0 && gpa <= 4.0) this.gpa = gpa; }

    public List<String> getCompletedCourses() { return new ArrayList<>(completedCourses); }

    public void addCompletedCourse(String courseCode) {
        if (!completedCourses.contains(courseCode)) {
            completedCourses.add(courseCode);
        }
    }

    public boolean hasCompletedCourse(String courseCode) {
        return completedCourses.contains(courseCode);
    }

    public void displayInfo() {
        System.out.println("\n========== STUDENT INFORMATION ==========");
        System.out.println("Student ID: " + studentId);
        System.out.println("Name: " + getFullName());
        System.out.println("Email: " + getEmail());
        System.out.println("Program: " + program);
        System.out.println("Year: " + year);
        System.out.println("GPA: " + String.format("%.2f", gpa));
        System.out.println("Completed Courses: " + completedCourses.size());
    }
    public String getRole() {
        return "STUDENT";
    }
    public void calculateGPA(Grade[] grades) {
        if (grades == null || grades.length == 0) {
            return;
        }

        double totalPoints = 0.0;
        int totalCredits = 0;

        for (Grade grade : grades) {
            if (grade.getLetterGrade() == null) continue;

            double gradePoints = convertLetterToGPA(grade.getLetterGrade());
            int credits = 3;

            totalPoints += gradePoints * credits;
            totalCredits += credits;
        }

        if (totalCredits > 0) {
            this.gpa = totalPoints / totalCredits;
        }
    }

    private double convertLetterToGPA(String letterGrade) {
        if (letterGrade == null) return 0.0;
        switch (letterGrade.toUpperCase()) {
            case "A": return 4.0;
            case "B": return 3.0;
            case "C": return 2.0;
            case "D": return 1.0;
            case "F": return 0.0;
            default: return 0.0;
        }
    }
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", program='" + program + '\'' +
                ", year=" + year +
                ", gpa=" + gpa +
                '}';
    }
}