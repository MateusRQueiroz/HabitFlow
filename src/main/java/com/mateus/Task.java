package com.mateus;

public class Task {
    private final String category;
    private final String description;
    private final String due_date;
    private String status;
    private final int priority;
    
    public Task(String category, String description, String due_date, String status, int priority) { 
        this.category = category;
        this.description = description;
        this.due_date = due_date;
        this.priority = priority; 
    }

    public void changeStatus(String new_status) {
        status = new_status;
    }

    public String getCategory() { return category;}
    public String getdescription() {return description;}
    public String getDue_date() {return due_date;}
    public int getPriority() {return priority;}
    public String getStatus() {return status;}
}
