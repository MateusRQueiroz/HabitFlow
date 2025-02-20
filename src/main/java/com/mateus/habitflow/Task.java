package com.mateus.habitflow;

import java.util.concurrent.atomic.AtomicInteger;

public class Task {
    private static final AtomicInteger count = new AtomicInteger(0);
    private final String category;
    private final String description;
    private final String due_date;
    private int status;
    private final int id;
    private final int priority;
        
    public Task(String category, String description, String due_date, int status, int priority) { 
        this.category = category;
        this.description = description;
        this.due_date = due_date;
        this.status = status;
        this.priority = priority; 
        this.id = count.incrementAndGet();
    }

    public void changeStatus(int new_status) {
        status = new_status;
    }

    public String getStatusString() {
        switch (status) {
            case 1 -> { return "overdue"; }
            case 2 -> { return "in progress"; }
            case 3 -> { return "pending"; }
            case 4 -> { return "on hold"; }
            default -> {
                System.out.println("Not an option");
                return null;
            }
        }
    }

    public String getPriorityString() {
        switch (priority) {
            case 1 -> { return "High"; }
            case 2 -> { return "Medium"; }
            case 3 -> { return "Low"; }
            case 4 -> { return "Optional"; }
            default -> {
                System.out.println("Not an option");
                return null;
            }
        }
    }

    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public String getDue_date() { return due_date; }
    public int getPriority() { return priority; }
    public int getStatus() { return status; }
    public int getId() { return id; }

    public static void updateCounter(int maxId) {
        count.set(maxId);
    }
}
