package com.mateus.habitflow;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public final class HabitManager {
    private HashMap<Integer, Habit> habits = new HashMap<>();
    Gson gson = new Gson();

    public HabitManager() {
        loadFromFile();
    }

    public void addHabit(String category, String description, int recurrence) {
        Habit habit = new Habit(category, description, recurrence);
        habits.put(habit.getId(), habit);
        saveToFile();
    }

    public void removeHabit(int id) {
        habits.remove(id);
        saveToFile();
    }

    public HashMap<Integer, Habit> getHabits() {
        return habits;
    }

    public void loadFromFile() {
        try (FileReader reader = new FileReader(Paths.get("Data", "habits.json").toString())) {
            Type habitsMapType = new TypeToken<HashMap<Integer, Habit>>() {}.getType();
            habits = gson.fromJson(reader, habitsMapType);
        }
        catch (IOException e) {
            System.out.println(e);
            System.out.println("Failed to load from habits file");
        }
    }

    public void saveToFile() {
        try (FileWriter writer = new FileWriter(Paths.get("Data", "habits.json").toString())) {
            gson.toJson(habits, writer);
        }
        catch (IOException e) {
            System.out.println(e);
            System.out.println("Failed to write into habits file");
        }
    }
}