package com.university.models;
public class LetterGradeCalculator extends GradeCalculator {

    public LetterGradeCalculator(String courseCode, String studentId) {
        super(courseCode, studentId);
    }

    @Override
    public String calculateGrade(double score) {
        if (score >= 90) return "A";
        else if (score >= 80) return "B";
        else if (score >= 70) return "C";
        else if (score >= 60) return "D";
        else return "F";
    }
    public double calculateGPA(String grade) {
        switch (grade.toUpperCase()) {
            case "A": return 4.0;
            case "B": return 3.0;
            case "C": return 2.0;
            case "D": return 1.0;
            default: return 0.0;
        }
    }
    public boolean isValidScore(double score) {
        return score >= 0 && score <= 100;
    }
}