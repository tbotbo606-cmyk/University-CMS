package com.university.repositories;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileRepository<T> {
    private final String filename;

    public FileRepository(String filename) {
        this.filename = filename;
    }

    public void save(List<T> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(data);
        } catch (IOException e) {
            System.out.println("Error saving to " + filename + ": " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> load() {
        File file = new File(filename);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}