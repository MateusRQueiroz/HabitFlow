package com.mateus.habitflow;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mateus.habitflow.serialization.LocalDateDeserializer;
import com.mateus.habitflow.serialization.LocalDateSerializer;

public final class TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private final Gson gson;

    public TaskManager() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .create();
        loadFromFile();
    }

    public void addTask(String category, String description, String due_date, int status, int priority) {
        Task task = new Task(category, description, due_date, status, priority);
        tasks.put(task.getId(), task);
        saveToFile();
    }

    public void removeTask(int id) {
        tasks.remove(id);
        saveToFile();
    }

    public ArrayList<Task> filterTasks(int filter, int criteria) {
        ArrayList<Task> filtered_tasks = new ArrayList<>(tasks.values());
        switch(filter) {
            case 1 -> {
                filtered_tasks.removeIf(task -> task.getPriority() != criteria);
                filtered_tasks.sort(Comparator.comparingInt(Task::getPriority));
                return filtered_tasks;
            }
            case 2 -> {
                filtered_tasks.removeIf(task -> task.getStatus() != criteria);
                filtered_tasks.sort(Comparator.comparingInt(Task::getStatus));
                return filtered_tasks;
            }
            default -> {
                System.out.println("Not a valid choice");
                return null;
            }
        }
    }

    public ArrayList<Task> getTasks(int order) {
        ArrayList<Task> ordered_tasks = new ArrayList<>(tasks.values());
        switch (order) {
            case 1 -> {
                ordered_tasks.sort(Comparator.comparingInt(Task::getPriority));
                return ordered_tasks;
            }
            case 2 -> {
                ordered_tasks.sort(Comparator.comparingInt(Task::getStatus));
                return ordered_tasks;
            }
            case 3 -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd");
                ordered_tasks.sort(Comparator.comparing(task -> LocalDate.parse(task.getDue_date(), formatter)));
                return ordered_tasks;
            }
            default -> {
                System.out.println("Not a valid choice");
                return null;
            }
        }
    }

    public void loadFromFile() {
        Path path = Paths.get("Data", "tasks.json");
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent()); 
                Files.createFile(path); 
                tasks = new HashMap<>();  
                return;  
            }
            try (FileReader reader = new FileReader(path.toString())) {
                Type tasksMapType = new TypeToken<HashMap<Integer, Task>>() {}.getType();
                tasks = gson.fromJson(reader, tasksMapType);
    
                if (tasks == null) {
                    tasks = new HashMap<>();
                }
    
                int maxId = tasks.keySet().stream().max(Integer::compare).orElse(0);
                Task.updateCounter(maxId);
            }
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Failed to load from tasks file");
        }
    }
    

    public void saveToFile() {
        try (FileWriter writer = new FileWriter(Paths.get("Data", "tasks.json").toString())) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Failed to save file.");
        }
    }
}
