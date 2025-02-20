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
            System.out.println("2. Remove Task");
            System.out.println("3. List Tasks");
            System.out.println("4. Change Task Status");
            System.out.println("5. Add Habit");
            System.out.println("6. Remove Habit");
            System.out.println("7. List Habits");
            System.out.println("8. Update Habit Progress"); 
            System.out.println("9. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 
            
            switch (choice) {
                case 1 -> addTask();
                case 2 -> removeTask();
                case 3 -> listTasks();
                case 4 -> changeTaskStatus();
                case 5 -> addHabit();
                case 6 -> removeHabit();
                case 7 -> listHabits();
                case 8 -> updateHabitProgress();
                case 9 -> {
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

    private static void removeTask() {
        System.out.print("Enter task ID to remove: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        if (taskManager.getTasks(1).stream().anyMatch(t -> t.getId() == id)) {
            taskManager.removeTask(id);
            System.out.println("Task removed successfully!");
        } else {
            System.out.println("Task with ID " + id + " not found.");
        }
    }
    


    private static void listTasks() {
        System.out.println();
        System.out.println("Ordered or Filtered: ");
        String orderOrFilter = scanner.nextLine().toLowerCase();
        ArrayList<Task> tasks = new ArrayList<>();
        switch (orderOrFilter) {
            case "ordered" -> {
                System.out.println();
                System.out.println("(1) Priority, (2) Status, or (3) Due Date: ");
                int order = scanner.nextInt();
                tasks = taskManager.getTasks(order); 
            }
            case "filtered" -> {
                System.out.println();
                System.out.println("Filtered by (1) Priority or (2) Status: ");
                int filter = scanner.nextInt();
                int criteria = 0;
                switch (filter) {
                    case 1 -> {
                        System.out.println();
                        System.out.println("Choose a priority number (1-4): ");
                        criteria = scanner.nextInt();
                    }
                    case 2 -> {
                        System.out.println();
                        System.out.println("Enter status (1-Overdue, 2-In Progress, 3-Pending, 4-On Hold): ");
                        criteria = scanner.nextInt();
                    }
                }
                tasks = taskManager.filterTasks(filter, criteria);
            }
        }

        if (tasks.isEmpty()) {
            System.out.println();
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

    private static void removeHabit() {
        System.out.print("Enter habit ID to remove: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        if (habitManager.getHabits().containsKey(id)) {
            habitManager.removeHabit(id);
            System.out.println("Habit removed successfully!");
        } else {
            System.out.println("Habit with ID " + id + " not found.");
        }
    }


    private static void listHabits() {
        Map<Integer, Habit> habits = habitManager.getHabits();
        if (habits.isEmpty()) {
            System.out.println("No habits available.");
            return;
        }

        System.out.println("\n===== Habit List =====");
        for (Habit habit : habits.values()) {
            System.out.printf("ID: %d | %s | %s | Streak: %d | Recurrence: %d per week | Completions this week: %d\n",
                    habit.getId(), habit.getCategory(), habit.getDescription(),
                    habit.getStreak(), habit.recurrence(), habit.getCompletions());
        }
    }

    private static void updateHabitProgress() {
        Map<Integer, Habit> habits = habitManager.getHabits();
        if (habits.isEmpty()) {
            System.out.println("No habits available to update.");
            return;
        }
        System.out.println("\nUpdating habit progress...");

        for (Habit habit : habits.values()) {
            System.out.printf("Have you completed '%s' for today? (yes/no): ", habit.getDescription());
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("yes") || input.equals("y")) {
                habit.updateHabitProgress();
                System.out.printf("Progress updated for habit '%s'.%n", habit.getDescription());
            } else {
                System.out.printf("No progress recorded for habit '%s'.%n", habit.getDescription());
            }
        }
        habitManager.saveToFile();
        System.out.println("All habit progress updated.");
    }
}
