package com.university.reports;

import com.university.models.Grade;
import com.university.models.Student;
import java.util.List;

public class Transcript extends Report {
    private Student student;
    private List<Grade> grades;

    public Transcript(String reportId, String generatedDate, Student student, List<Grade> grades) {
        super(reportId, "OFFICIAL ACADEMIC TRANSCRIPT", generatedDate);
        this.student = student;
        this.grades = grades;
    }
    public String generateReport() {
        if (!validate()) return "Error: Invalid Data for Transcript.";

        StringBuilder sb = new StringBuilder();
        sb.append(formatHeader());
        sb.append("Student Name: ").append(student.getFullName()).append("\n");
        sb.append("Student ID:   ").append(student.getStudentId()).append("\n");
        sb.append("Program:      ").append(student.getProgram()).append("\n");
        sb.append("Current GPA:  ").append(String.format("%.2f", student.getGpa())).append("\n");
        sb.append("Course Code | Score | Grade\n");
        if (grades.isEmpty()) {
            sb.append("No grades recorded yet.\n");
        } else {
            for (Grade g : grades) {
                sb.append(String.format("%-11s | %-5.1f | %s\n",
                        g.getCourseCode(), g.getScore(), g.getLetterGrade()));
            }
        }
        return sb.toString();
    }
    public String getReportType() {
        return "TRANSCRIPT";
    }
    public boolean validate() {
        return student != null;
    }
}