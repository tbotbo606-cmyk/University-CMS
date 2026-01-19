package com.university.repositories;

import com.university.models.*;
import java.util.ArrayList;
import java.util.List;

public class UniversityDataRepository implements DataRepository {

    private FileRepository<User> userRepo;
    private FileRepository<Course> courseRepo;
    private FileRepository<Enrollment> enrollmentRepo;
    private FileRepository<Grade> gradeRepo;
    private List<User> users;
    private List<Course> courses;
    private List<Enrollment> enrollments;
    private List<Grade> grades;
    public UniversityDataRepository() {
        userRepo = new FileRepository<>("users.dat");
        courseRepo = new FileRepository<>("courses.dat");
        enrollmentRepo = new FileRepository<>("enrollments.dat");
        gradeRepo = new FileRepository<>("grades.dat");
        loadAllData();
    }
    public boolean loadAllData() {
        users = userRepo.load();
        if (users == null) users = new ArrayList<>();
        courses = courseRepo.load();
        if (courses == null) courses = new ArrayList<>();
        enrollments = enrollmentRepo.load();
        if (enrollments == null) enrollments = new ArrayList<>();
        grades = gradeRepo.load();
        if (grades == null) grades = new ArrayList<>();

        return true;
    }
    public boolean saveAllData() {
        userRepo.save(users);
        courseRepo.save(courses);
        enrollmentRepo.save(enrollments);
        gradeRepo.save(grades);
        return true;
    }
    public void clearData() {
        users.clear();
        courses.clear();
        enrollments.clear();
        grades.clear();
    }
    public boolean saveUser(User user) {
        User existing = getUserById(user.getUserId());
        if (existing != null) {
            updateUser(user);
        } else {
            users.add(user);
        }
        return saveAllData();
    }
    public User getUserById(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
    public User[] getAllUsers() {
        return users.toArray(new User[0]);
    }
    public boolean updateUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(user.getUserId())) {
                users.set(i, user);
                return saveAllData();
            }
        }
        return false;
    }
    public boolean deleteUser(String userId) {
        User user = getUserById(userId);
        if (user != null) {
            users.remove(user);
            return saveAllData();
        }
        return false;
    }
    public boolean saveCourse(Course course) {
        Course existing = getCourseByCode(course.getCourseCode());
        if (existing != null) {
            updateCourse(course);
        } else {
            courses.add(course);
        }
        return saveAllData();
    }
    public Course getCourseByCode(String courseCode) {
        for (Course c : courses) {
            if (c.getCourseCode().equalsIgnoreCase(courseCode)) {
                return c;
            }
        }
        return null;
    }
    public Course[] getAllCourses() {
        return courses.toArray(new Course[0]);
    }
    public boolean updateCourse(Course course) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCourseCode().equalsIgnoreCase(course.getCourseCode())) {
                courses.set(i, course);
                return saveAllData();
            }
        }
        return false;
    }
    public boolean deleteCourse(String courseCode) {
        Course c = getCourseByCode(courseCode);
        if (c != null) {
            courses.remove(c);
            return saveAllData();
        }
        return false;
    }
    public boolean saveEnrollment(Enrollment enrollment) {
        Enrollment existing = getEnrollmentById(enrollment.getEnrollmentId());
        if (existing != null) {
            updateEnrollment(enrollment);
        } else {
            enrollments.add(enrollment);
        }
        return saveAllData();
    }
    public Enrollment getEnrollmentById(String enrollmentId) {
        for (Enrollment e : enrollments) {
            if (e.getEnrollmentId().equals(enrollmentId)) {
                return e;
            }
        }
        return null;
    }
    public Enrollment[] getEnrollmentsByStudent(String studentId) {
        List<Enrollment> list = new ArrayList<>();
        for (Enrollment e : enrollments) {
            if (e.getStudentId().equals(studentId)) {
                list.add(e);
            }
        }
        return list.toArray(new Enrollment[0]);
    }
    public Enrollment[] getEnrollmentsByCourse(String courseCode) {
        List<Enrollment> list = new ArrayList<>();
        for (Enrollment e : enrollments) {
            if (e.getCourseCode().equalsIgnoreCase(courseCode)) {
                list.add(e);
            }
        }
        return list.toArray(new Enrollment[0]);
    }
    public boolean updateEnrollment(Enrollment enrollment) {
        for (int i = 0; i < enrollments.size(); i++) {
            if (enrollments.get(i).getEnrollmentId().equals(enrollment.getEnrollmentId())) {
                enrollments.set(i, enrollment);
                return saveAllData();
            }
        }
        return false;
    }
    public boolean deleteEnrollment(String enrollmentId) {
        Enrollment e = getEnrollmentById(enrollmentId);
        if (e != null) {
            enrollments.remove(e);
            return saveAllData();
        }
        return false;
    }
    public boolean saveGrade(Grade grade) {
        Grade existing = getGradeById(grade.getGradeId());
        if (existing != null) {
            updateGrade(grade);
        } else {
            grades.add(grade);
        }
        return saveAllData();
    }
    public Grade getGradeById(String gradeId) {
        for (Grade g : grades) {
            if (g.getGradeId().equals(gradeId)) {
                return g;
            }
        }
        return null;
    }
    public Grade[] getGradesByStudent(String studentId) {
        List<Grade> list = new ArrayList<>();
        for (Grade g : grades) {
            if (g.getStudentId().equals(studentId)) {
                list.add(g);
            }
        }
        return list.toArray(new Grade[0]);
    }
    public Grade[] getGradesByCourse(String courseCode) {
        List<Grade> list = new ArrayList<>();
        for (Grade g : grades) {
            if (g.getCourseCode().equalsIgnoreCase(courseCode)) {
                list.add(g);
            }
        }
        return list.toArray(new Grade[0]);
    }
    public boolean updateGrade(Grade grade) {
        for (int i = 0; i < grades.size(); i++) {
            if (grades.get(i).getGradeId().equals(grade.getGradeId())) {
                grades.set(i, grade);
                return saveAllData();
            }
        }
        return false;
    }
    public boolean deleteGrade(String gradeId) {
        Grade g = getGradeById(gradeId);
        if (g != null) {
            grades.remove(g);
            return saveAllData();
        }
        return false;
    }
}