package com.mateus.habitflow;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public final class TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    Gson gson = new Gson();

    public TaskManager() {
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
            //priority
            case 1 -> {
                filtered_tasks.removeIf(task -> task.getPriority() != criteria);
                filtered_tasks.sort(Comparator.comparingInt(Task::getPriority));
                return filtered_tasks;
            }
            //status
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
            // High, medium, low, optional
            case 1 -> {
                ordered_tasks.sort(Comparator.comparingInt(Task::getPriority));
                return ordered_tasks;
            }
            // overdue, In progress, pending, on hold, completed, cancelled
            case 2 ->  {
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
        try (FileReader reader = new FileReader(Paths.get("data", "tasks.json").toString())) {
            Type tasksMapType = new TypeToken<HashMap<Integer, Task>>() {}.getType();
            tasks = gson.fromJson(reader, tasksMapType);
            LocalDate today = LocalDate.now();
            for (Task task : tasks.values()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd");
                LocalDate dueDate = LocalDate.parse(task.getDue_date(), formatter);
                if (today.isAfter(dueDate)) {
                    task.changeStatus(1);
                }
            }
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Failed to load from file.");
        }
    }

    public void saveToFile() {
        try (FileWriter writer = new FileWriter(Paths.get("data", "tasks.json").toString())) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Failed to save file.");
        }
    }
}
