package com.mateus.habitflow;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public final class taskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    Gson gson = new Gson();

    public taskManager() {
        loadFromFile();
    }

    public void addTask(String category, String description, String due_date, String status, int priority) {
        Task task = new Task(category, description, due_date, status, priority);
        tasks.put(tasks.size(), task);
        saveToFile();
    }

    public void removeTask(int i) {
        tasks.remove(i);
        saveToFile();
    }

    public void loadFromFile() {
        try (FileReader reader = new FileReader("src\\main\\java\\com\\mateus\\tasks.json")) {
            Type tasksMapType = new TypeToken<HashMap<Integer, Task>>() {}.getType();
            tasks = gson.fromJson(reader, tasksMapType);
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Failed to load from file.");
        }
    }

    public void saveToFile() {
        try (FileWriter writer = new FileWriter("src\\main\\java\\com\\mateus\\tasks.json")) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Failed to save file.");
        }
    }
}
