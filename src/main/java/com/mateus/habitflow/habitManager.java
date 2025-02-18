package com.mateus.habitflow;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public final class habitManager {
    private HashMap<Integer, Habit> habits = new HashMap<>();
    Gson gson = new Gson();

    public habitManager() {
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
        try (FileReader reader = new FileReader("src\\main\\java\\com\\mateus\\habitflow\\habits.json")) {
            Type habitsMapType = new TypeToken<HashMap<Integer, Habit>>() {}.getType();
            habits = gson.fromJson(reader, habitsMapType);
        }
        catch (IOException e) {
            System.out.println(e);
            System.out.println("Failed to load from habits file");
        }
    }

    public void saveToFile() {
        try (FileWriter writer = new FileWriter("src\\main\\java\\com\\mateus\\habitflow\\habits.json")) {
            gson.toJson(habits, writer);
        }
        catch (IOException e) {
            System.out.println(e);
            System.out.println("Failed to write into habits file");
        }
    }
}