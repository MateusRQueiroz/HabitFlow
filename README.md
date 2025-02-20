# HabitFlow

HabitFlow is a command-line application designed to help users manage their tasks and habits. It allows users to add, remove, and track tasks and habits, filter them by priority or status, and save their progress to a file. It also has a streak system for each habit that rewards users that accomplish their weekly goal.

## Features

### Task Management: 

- Add tasks with a category, description, due date, status, and priority.
- Remove tasks by ID.
- Filter tasks by priority or status
- List tasks sorted by priority, status, or due date.

### Habit Management:

- Add habits with a category, description, and weekly recurrence goal.
- Track habit streaks and completions.
- List all habits with their current streak and recurrence.

### Persistence 

- Save tasks and habits to JSON files (tasks.json and habits.json).
- Load tasks and habits from JSON files on startup.

## Prerequisites

- Java Development Kit (JDK): Version 14 or higher.
- Maven: For building and running the project.

## Setup 

1. Clone the Repository: 
 ```git clone https://github.com/your-username/habitflow.git```
 ```cd habitflow```

2. Build the project:
- Use maven to build the project.
```mvn clean package```

3. Run the application:
- Use Maven to run the application.
```mvn exec:java```

## Usage

### Main Menu 
- When you run the application, you will see the following menu:
![alt text](image.png)

### Adding a Task 
1. Select 1. Add Task.
2. Enter the task details: 
- Category
- Description
- Due Date (format: yyyy-MM-dd)
- Status (1-Overdue, 2-In Progress, 3-Pending, 4-On Hold)
- Priority (1-High, 2-Medium, 3-Low, 4-Optional)

### Removing a Task 
1. Select 2. Remove Task
2. Enter Task ID.

### Listing Tasks
1. Select 3. List Tasks.
2. Choose between ordered tasks or filtered tasks.
- If you chose ordered, select between by (1) priority, (2) status, or (3) due date..
- If you chose filtered, select between (1) priority or (2) status, then what status or priority number.
3. Tasks will be displayed accordingly. 

### Changing Task Status 
1. Select 4. Change Task Status.
2. Enter the Task ID and the new status.

### Adding a Habit
1. Select 5. Add Habit.
2. Enter habit details:
- Category
- Description 
- Recurrence per week

### Removing Habits 
1. Select 6. Remove Habit.
2. Enter Habit ID.

### Listing Habits
1. Select 7. List Habits.
2. Habits will be displayed with their current streak and recurrence.

### Updating Habit Progress
1. Select 8. Update Habit Progress.
2. Answer completion questions.

### Exiting the Application
1. Select 9. Exit to close the application.

## Dependencies
- Gson: For JSON serialization and deserialization.
- JUnit: For unit testing (optional).

## Contributing
Contributions are welcome! If you'd like to contribute, follow following steps:

1. Fork the repository.
2. Create a new branch for your feature or bugfix.
3. Commit your changes.
4. Submit a pull request.

## License 
This project is licensed under the MIT License. See the LICENSE file for details.