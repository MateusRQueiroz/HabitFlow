package com.mateus.habitflow;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mateus.habitflow.serialization.LocalDateDeserializer;
import com.mateus.habitflow.serialization.LocalDateSerializer;

public final class HabitManager {
    private HashMap<Integer, Habit> habits = new HashMap<>();
    private final Gson gson;

    public HabitManager() {
        gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateSerializer()) 
            .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer()) 
            .create();

        try {
            loadFromFile();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Unable to load file");
        }
    }

    public void addHabit(String category, String description, int recurrence) {
        Habit habit = new Habit(category, description, recurrence);
        habits.put(habit.getId(), habit);
        try {
            saveToFile();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Unable to save file");
        }
    }

    public void removeHabit(int id) {
        habits.remove(id);
        saveToFile();
    }

    public HashMap<Integer, Habit> getHabits() {
        return habits;
    }

    public void loadFromFile() {
        Path path = Paths.get("Data", "habits.json");
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent()); 
                Files.createFile(path); 
                habits = new HashMap<>();  
                return;  
            }
            try (FileReader reader = new FileReader(path.toString())) {
                Type habitsMapType = new TypeToken<HashMap<Integer, Habit>>() {}.getType();
                habits = gson.fromJson(reader, habitsMapType);
    
                if (habits == null) {
                    habits = new HashMap<>();
                }
                int maxId = habits.keySet().stream().max(Integer::compare).orElse(0);
                Habit.updateCounter(maxId);
            }
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Failed to load from habits file");
        }
    }
    
    public void saveToFile() {
        try (FileWriter writer = new FileWriter(Paths.get("Data", "habits.json").toString())) {
            gson.toJson(habits, writer);
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Failed to write into habits file");
        }
    }
}
