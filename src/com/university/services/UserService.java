package com.university.services;

import com.university.models.User;
import com.university.models.Admin;
import com.university.repositories.DataRepository;
import com.university.repositories.UniversityDataRepository;

public class UserService {
    private final DataRepository repository;
    public UserService() {
        this.repository = new UniversityDataRepository();
        initializeDefaultAdmin();
    }
    private void initializeDefaultAdmin() {
        if (repository.getAllUsers().length == 0) {
            System.out.println("System empty. Creating default admin...");
            Admin admin = new Admin("AD-001", "admin", "admin123", "admin@uni.edu", "System Administrator", "IT");

            repository.saveUser(admin);
            System.out.println("-> Default Admin created: Username=admin, Password=admin123");
        }
    }
    public User login(String username, String password) {
        User[] users = repository.getAllUsers();
        for (User user : users) {
            if (user != null && user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}