package com.university.services;

import com.university.models.Course;
import com.university.repositories.DataRepository;
import com.university.repositories.UniversityDataRepository;

public class CourseService {
    private final DataRepository repository;
    public CourseService() {
        this.repository = new UniversityDataRepository();
    }
    public void createCourse(String code, String title, int credits, String instructorId, int capacity) {

        if (repository.getCourseByCode(code) != null) {
            System.out.println("Error: Course with code " + code + " already exists!");
            return;
        }

        Course newCourse = new Course(code, title, credits, instructorId, capacity);
        if (repository.saveCourse(newCourse)) {
            System.out.println("Success: Course [" + code + "] created successfully.");
        } else {
            System.out.println("Error: Failed to save course.");
        }
    }
    public Course[] listAllCourses() {
        return repository.getAllCourses();
    }
    public Course getCourse(String code) {
        return repository.getCourseByCode(code);
    }
    public void deleteCourse(String code) {
        if (repository.deleteCourse(code)) {
            System.out.println("Success: Course [" + code + "] deleted.");
        } else {
            System.out.println("Error: Course not found or could not be deleted.");
        }
    }
}