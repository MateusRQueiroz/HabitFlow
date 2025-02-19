package com.mateus.habitflow;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TaskManager taskManager = new TaskManager();
    private static final HabitManager habitManager = new HabitManager();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== HabitFlow CLI =====");
            System.out.println("1. Add Task");
            System.out.println("2. List Tasks");
            System.out.println("3. Change Task Status");
            System.out.println("4. Add Habit");
            System.out.println("5. List Habits");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 
            
            switch (choice) {
                case 1 -> addTask();
                case 2 -> listTasks();
                case 3 -> changeTaskStatus();
                case 4 -> addHabit();
                case 5 -> listHabits();
                case 6 -> {
                    System.out.println("Exiting HabitFlow...");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addTask() {
        System.out.print("Enter task category: ");
        String category = scanner.nextLine();
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();
        System.out.print("Enter due date (yyyy-MM-dd): ");
        String dueDate = scanner.nextLine();
        System.out.print("Enter status (1-Overdue, 2-In Progress, 3-Pending, 4-On Hold): ");
        int status = scanner.nextInt();
        System.out.print("Enter priority (1-High, 2-Medium, 3-Low, 4-Optional): ");
        int priority = scanner.nextInt();
        scanner.nextLine(); 

        taskManager.addTask(category, description, dueDate, status, priority);
        System.out.println("Task added successfully!");
    }

    private static void listTasks() {
        ArrayList<Task> tasks = taskManager.getTasks(1); 
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }

        System.out.println("\n===== Task List =====");
        for (Task task : tasks) {
            System.out.printf("ID: %d | %s | %s | Due: %s | Status: %s | Priority: %s\n",
                    task.getId(), task.getCategory(), task.getDescription(), task.getDue_date(),
                    task.getStatusString(), task.getPriorityString());
        }
    }

    private static void changeTaskStatus() {
        System.out.print("Enter Task ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter new status (1-Overdue, 2-In Progress, 3-Pending, 4-On Hold): ");
        int newStatus = scanner.nextInt();
        scanner.nextLine(); 

        Task task = taskManager.getTasks(1).stream().filter(t -> t.getId() == id).findFirst().orElse(null);
        if (task != null) {
            task.changeStatus(newStatus);
            taskManager.saveToFile();
            System.out.println("Task status updated successfully!");
        } else {
            System.out.println("Task not found.");
        }
    }

    private static void addHabit() {
        System.out.print("Enter habit category: ");
        String category = scanner.nextLine();
        System.out.print("Enter habit description: ");
        String description = scanner.nextLine();
        System.out.print("Enter recurrence per week: ");
        int recurrence = scanner.nextInt();
        scanner.nextLine(); 

        habitManager.addHabit(category, description, recurrence);
        System.out.println("Habit added successfully!");
    }

    private static void listHabits() {
        Map<Integer, Habit> habits = habitManager.getHabits();
        if (habits.isEmpty()) {
            System.out.println("No habits available.");
            return;
        }

        System.out.println("\n===== Habit List =====");
        for (Habit habit : habits.values()) {
            System.out.printf("ID: %d | %s | %s | Streak: %d | Recurrence: %d per week\n",
                    habit.getId(), habit.getCategory(), habit.getDescription(),
                    habit.getStreak(), habit.recurrence());
        }
    }
}
