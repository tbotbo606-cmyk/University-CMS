package com.university.reports;

import com.university.models.Course;
import com.university.models.Student;
import java.util.List;

public class CourseRoster extends Report {
    private Course course;
    private List<Student> students;

    public CourseRoster(String reportId, String generatedDate, Course course, List<Student> students) {
        super(reportId, "COURSE ROSTER REPORT", generatedDate);
        this.course = course;
        this.students = students;
    }
    public String generateReport() {
        if (!validate()) return "Error: Invalid Data for Roster.";

        StringBuilder sb = new StringBuilder();
        sb.append(formatHeader());
        sb.append("Course: ").append(course.getCourseCode()).append(" - ").append(course.getTitle()).append("\n");
        sb.append("Credits: ").append(course.getCredits()).append("\n");
        sb.append("Total Enrolled: ").append(students.size()).append(" / ").append(course.getCapacity()).append("\n");
        sb.append("ID         | Student Name\n");
        if (students.isEmpty()) {
            sb.append("No students enrolled.\n");
        } else {
            for (Student s : students) {
                sb.append(String.format("%-10s | %s\n", s.getStudentId(), s.getFullName()));
            }
        }

        return sb.toString();
    }
    public String getReportType() {
        return "ROSTER";
    }
    public boolean validate() {
        return course != null;
    }
}
