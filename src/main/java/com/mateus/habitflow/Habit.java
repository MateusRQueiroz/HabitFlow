package com.mateus.habitflow;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class Habit {
    private final String category;
    private final String description; 
    private final int recurrence; //completions per week
    private int completionsThisWeek; 
    private int streak;
    private LocalDate startOfWeek;

    public Habit(String category, String description, int recurrence) { 
        this.category = category;
        this.description = description;
        this.recurrence = recurrence;
        this.streak = 0; 
        this.completionsThisWeek = 0;
        this.startOfWeek = setStartOfWeek();
    }

    public final LocalDate setStartOfWeek() {
        LocalDate today = LocalDate.now();
        startOfWeek = today.with(DayOfWeek.MONDAY);
        return startOfWeek;
    }

    public boolean isNewWeek(){
        LocalDate today = LocalDate.now();
        return (today.isAfter(startOfWeek.plusDays(6)));
    }

    public void startNewWeek() {
        startOfWeek = setStartOfWeek();
        completionsThisWeek = 0;
        if (completionsThisWeek >= recurrence) {
            streak++;
        }
        else {
            streak = 0;
        }
    }
    
    public void updateHabitProgress() {
        if (isNewWeek()) {
            startNewWeek();
        }
        completionsThisWeek++;
    }

    public String getCategory() { return category;}
    public String getDescription() { return description; }
    public int recurrence() { return recurrence; }
    public int getStreak() { return streak; }
    public int getCompletions() { return completionsThisWeek; }

}
