package com.university.models;

public class Admin extends User {
    private static final long serialVersionUID = 1L;

    private String department;

    public Admin(String userId, String username, String password, String email, String fullName, String department) {
        super(userId, username, password, email, fullName);
        this.department = department;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public void displayInfo() {
        System.out.println("\n========== ADMIN PROFILE ==========");
        System.out.println("Admin ID: " + getUserId());
        System.out.println("Name: " + getFullName());
        System.out.println("Email: " + getEmail());
        System.out.println("Department: " + department);
        System.out.println("Role: SYSTEM ADMINISTRATOR");
        System.out.println("===================================\n");
    }
    public String getRole() {
        return "ADMIN";
    }
    public String toString() {
        return "Admin{" +
                "userId='" + getUserId() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}