package com.university.models;

import java.io.Serializable;
public class Enrollment implements Serializable {
    private static final long serialVersionUID = 1L;

    private String enrollmentId;
    private String studentId;
    private String courseCode;
    private String semester;
    private String status;

    public Enrollment(String enrollmentId, String studentId, String courseCode, String semester) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.semester = semester;
        this.status = "ACTIVE";
    }
    public String getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(String enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String toString() {
        return "Enrollment{" +
                "enrollmentId='" + enrollmentId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", semester='" + semester + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}