import entity.Task;

import java.util.Random;
import java.util.Scanner;

public class TaskManager {
    static TaskList taskList = new TaskList();
    public static Scanner scanner = new Scanner(System.in);

    public static void startToDo(){
        while (true) {
            System.out.println("\nWelcome to Zindel's Task Management System!\nPlease enter the number of the option you wish to select\n");
            System.out.println("1. Add a new task");
            System.out.println("2. Remove a task");
            System.out.println("3. Display all Tasks");
            System.out.println("4. Complete a task");
            System.out.println("5. View all completed tasks");
            System.out.println("6. View all incomplete tasks");
            System.out.println("7. Exit the Task Management System");

            switch (scanner.nextLine()) {
                case "1": addTask();
                    break;
                case "2": removeTask();
                    break;
                case "3": displayAllTasks();
                    break;
                case "4": updateTask();
                    break;
                case "5": viewCompletedTasks();
                    break;
                case "6": viewIncompleteTasks();
                    break;
                case "7": {
                    System.out.println("Thank you for using Zindel's Task Management System");
                    System.exit(0);
                    break;
                }
                default:
                    System.err.println("Error: invalid entry.\n");
                    break;
            }
        }
    }

    public static void addTask() {
        String taskName;
        System.out.println("\nPlease enter the name of the task you wish to add or enter 'c' to cancel");
        taskName = scanner.nextLine();
        String id = generateTaskId();

        if (!taskName.equalsIgnoreCase("c")) {
            Task t = new Task(taskName, false, id);
            taskList.add(t);
            System.out.println("Task: " + t.getTaskName() + " added successfully.");
        } else {
            System.out.println("Add task operation cancelled.");
        }
    }

    public static void removeTask() {
        if (taskList.isEmpty()) {
            System.out.println("There are no tasks to remove.");
            return;
        }

        System.out.println("\nPlease enter the ID of the task you wish to remove");
        displayAllTasks();

        String idToRemove = scanner.nextLine();
        Task taskToRemove = getTaskByID(idToRemove);

        if (taskToRemove == null) {
            System.out.println("No task found with ID: " + idToRemove);
            return;
        }

        while (true) {
            System.out.println("Are you sure you'd like to remove this task?");
            System.out.println(taskToRemove.getTaskName());
            System.out.print("Enter 'y' to confirm, or 'c' to cancel: \n");

            String confirmation = scanner.nextLine().trim().toLowerCase();
            if (confirmation.equals("y")) {
                taskList.remove(taskToRemove);
                System.out.println("Task: " + taskToRemove.getTaskName() + " removed successfully.");
                break;
            } else if (confirmation.equals("c")) {
                System.out.println("Task removal canceled.");
                break;
            } else {
                System.err.println("Error: invalid entry. Please enter 'y' or 'c'.");
            }
        }
    }

    public static void displayAllTasks() {
        if (!taskList.isEmpty()) {
            System.out.println("Tasks: ");
            for (Task task : taskList) {
                System.out.println(task.getTaskInformation());
            }
        } else {
            System.out.println("No tasks to display. Add some now!");
        }
    }

    public static void updateTask(){
        if (taskList.isEmpty()) {
            System.out.println("There are no tasks to complete. Add some now!");
            return;
        }

        System.out.println("\nPlease enter the ID of the task you wish to complete");
        displayAllTasks();
        String idToUpdate = scanner.nextLine();
        Task taskToUpdate = getTaskByID(idToUpdate);

        if (taskToUpdate == null) {
            System.out.println("No task found with ID: " + idToUpdate);
            return;
        }
        taskToUpdate.setCompletionStatus(true);
        System.out.println("Task " + taskToUpdate.getTaskName() + " completed successfully.");
        System.out.println(taskToUpdate.getTaskInformation());
    }
    public static void viewCompletedTasks() {
        taskList.getCompletedTasks();
    }

    public static void viewIncompleteTasks() {
        taskList.getIncompleteTasks();
    }


    public static Task getTaskByID(String id) {
        for (Task task : taskList) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;
    }

    public static String generateTaskId() {
        Random random = new Random();
        String uniqueId = String.valueOf(1 + random.nextInt(100));
        if (getTaskByID(uniqueId) == null) return uniqueId;
        else return generateTaskId();
    }
}