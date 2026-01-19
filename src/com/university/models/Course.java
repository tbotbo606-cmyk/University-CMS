package com.university.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    private String courseCode;
    private String title;
    private int credits;
    private String instructorId;
    private int capacity;
    private List<String> enrolledStudents;
    private List<String> prerequisites;

    public Course(String courseCode, String title, int credits, String instructorId, int capacity) {
        this.courseCode = courseCode;
        this.title = title;
        this.credits = credits;
        this.instructorId = instructorId;
        this.capacity = capacity;
        this.enrolledStudents = new ArrayList<>();
        this.prerequisites = new ArrayList<>();
    }
    public String getCourseCode() {
        return courseCode;
    }
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        if (credits > 0) {
            this.credits = credits;
        }
    }

    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        if (capacity > 0) {
            this.capacity = capacity;
        }
    }

    public List<String> getEnrolledStudents() {
        return new ArrayList<>(enrolledStudents);
    }

    public List<String> getPrerequisites() {
        return new ArrayList<>(prerequisites);
    }

    public void addPrerequisite(String courseCode) {
        if (!prerequisites.contains(courseCode)) {
            prerequisites.add(courseCode);
        }
    }

    public boolean isFull() {
        return enrolledStudents.size() >= capacity;
    }

    public boolean addStudent(String studentId) {
        if (!isFull() && !enrolledStudents.contains(studentId)) {
            enrolledStudents.add(studentId);
            return true;
        }
        return false;
    }

    public boolean removeStudent(String studentId) {
        return enrolledStudents.remove(studentId);
    }

    public int getEnrolledCount() {
        return enrolledStudents.size();
    }

    public String toString() {
        return "Course{" +
                "courseCode='" + courseCode + '\'' +
                ", title='" + title + '\'' +
                ", credits=" + credits +
                ", capacity=" + capacity +
                ", enrolled=" + enrolledStudents.size() +
                '}';
    }
}