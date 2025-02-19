
package com.mateus.habitflow;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class Habit {
    private static final AtomicInteger count = new AtomicInteger(0);
    private final String category;
    private final String description; 
    private final int recurrence; //completions per week
    private int completionsThisWeek; 
    private final int id;
    private int streak;
    private LocalDate startOfWeek;

    public Habit(String category, String description, int recurrence) {
        this.id = count.incrementAndGet(); 
        this.category = category;
        this.description = description;
        this.recurrence = recurrence;
        this.streak = 0; 
        this.completionsThisWeek = 0;   
        this.startOfWeek = setStartOfWeek();
    }

    public final LocalDate setStartOfWeek() {
        LocalDate today = LocalDate.now();
        return today.with(DayOfWeek.MONDAY);
    }

    public boolean isNewWeek(){
        LocalDate today = LocalDate.now();
        return (today.isAfter(startOfWeek.plusDays(6)));
    }

    public void startNewWeek() {
        if (completionsThisWeek >= recurrence) {
            streak++;
        } else {
            streak = 0;
        }
        startOfWeek = setStartOfWeek();
        completionsThisWeek = 0;
    }

    public void updateHabitProgress() {
        if (isNewWeek()) {
            startNewWeek();
        }
        completionsThisWeek++;
    }

    public int getId() { return id; }
    public String getCategory() { return category;}
    public String getDescription() { return description; }
    public int recurrence() { return recurrence; }
    public int getStreak() { return streak; }
    public int getCompletions() { return completionsThisWeek; }
}