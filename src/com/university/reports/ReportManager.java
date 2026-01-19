package com.university.reports;

import com.university.models.*;
import com.university.repositories.DataRepository;
import com.university.repositories.UniversityDataRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ReportManager implements ReportGenerator {

    private final DataRepository repository;

    public ReportManager() {
        this.repository = new UniversityDataRepository();
    }
    public Report generateTranscript(String studentId) {
        User user = repository.getUserById(studentId);
        if (user == null || !(user instanceof Student)) {
            System.out.println("Error: Student not found.");
            return null;
        }
        Grade[] gradesArray = repository.getGradesByStudent(studentId);
        List<Grade> gradeList = Arrays.asList(gradesArray);
        String reportId = "REP-" + UUID.randomUUID().toString().substring(0, 8);
        return new Transcript(reportId, new Date().toString(), (Student) user, gradeList);
    }
    public Report generateCourseRoster(String courseCode) {
        Course course = repository.getCourseByCode(courseCode);
        if (course == null) {
            System.out.println("Error: Course not found.");
            return null;
        }
        Enrollment[] enrollments = repository.getEnrollmentsByCourse(courseCode);
        List<Student> studentList = new ArrayList<>();

        for (Enrollment e : enrollments) {
            User u = repository.getUserById(e.getStudentId());
            if (u instanceof Student) {
                studentList.add((Student) u);
            }
        }
        String reportId = "REP-" + UUID.randomUUID().toString().substring(0, 8);
        return new CourseRoster(reportId, new Date().toString(), course, studentList);
    }
    public boolean saveReport(Report report, String filename) {
        if (report == null || !report.validate()) return false;

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(report.generateReport());
            System.out.println("Success: Report saved to " + filename);
            return true;
        } catch (IOException e) {
            System.out.println("Error saving report: " + e.getMessage());
            return false;
        }
    }
    public Report generateTeachingLoad(String instructorId) {
        System.out.println("Feature not implemented yet.");
        return null;
    }
    public Report generateDepartmentSummary(String department) {
        System.out.println("Feature not implemented yet.");
        return null;
    }
}
