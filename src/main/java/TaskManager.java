import entity.Task;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Scanner;

public class TaskManager {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static TaskList taskList;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            emf = Persistence.createEntityManagerFactory("default");
            em = emf.createEntityManager();

            taskList = new TaskList(em);
            taskList.loadFromDatabase();

            startToDo();
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }
    }

    public static void startToDo() {
        while (true) {
            System.out.println("\nWelcome to Zindel's Task Management System!");
            System.out.println("1. Add a new task");
            System.out.println("2. Remove a task");
            System.out.println("3. Display all Tasks");
            System.out.println("4. Complete a task");
            System.out.println("5. View all completed tasks");
            System.out.println("6. View all incomplete tasks");
            System.out.println("7. Exit");

            switch (scanner.nextLine()) {
                case "1" -> addTask();
                case "2" -> removeTask();
                case "3" -> displayAllTasks();
                case "4" -> completeTask();
                case "5" -> taskList.printCompletedTasks();
                case "6" -> taskList.printIncompleteTasks();
                case "7" -> {
                    System.out.println("Thank you for using Zindel's Task Manager!");
                    System.exit(0);
                }
                default -> System.err.println("Error: Invalid entry. Select the number of the option you'd like to select!");
            }
        }
    }

    public static void addTask() {
        System.out.println("Enter task name (or 'c' to cancel):");
        String name = scanner.nextLine();
        if (!name.equalsIgnoreCase("c")) {
            Task t = new Task();
            t.setTaskName(name);
            t.setCompletionStatus(false);
            taskList.addTask(t);
            System.out.println("Task added: " + t.getTaskInformation());
        } else {
            System.out.println("Canceling... No tasks have been added.");
        }
    }

    public static void removeTask() {
        displayAllTasks();
        System.out.println("Enter the ID of the task to remove:");
        String input = scanner.nextLine();
        try {
            Integer id = Integer.parseInt(input);
            Task t = taskList.getTaskByID(id);
            if (t != null) {
                System.out.println("Are you sure you want to remove: " + t.getTaskName() + "? \nEnter 'y' for yes or 'c' to cancel");
                String confirm = scanner.nextLine();
                if (confirm.equalsIgnoreCase("y")) {
                    taskList.removeTask(t);
                    System.out.println("Task removed: " + t.getTaskInformation());
                } else if (confirm.equalsIgnoreCase("c")) {
                    System.out.println("Canceling... No tasks have been removed.");
                } else {
                    System.out.println("Error: Invalid entry. Please try again");
                }
            } else System.out.println("Task not found.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID entered.");
        }
    }

    public static void displayAllTasks() {
        if (taskList.isEmpty()) System.out.println("No tasks to display.");
        else {
            System.out.println("|------------------------Task List------------------------|");
            taskList.forEach(t -> System.out.println(t.getTaskInformation()));
            System.out.println("|---------------------------------------------------------|");
        }
    }

    public static void completeTask() {
        displayAllTasks();
        System.out.println("Enter the ID of the task to complete:");
        String input = scanner.nextLine();
        try {
            Integer id = Integer.parseInt(input);
            Task t = taskList.getTaskByID(id);
            if (t == null) {
                System.out.println("Task not found.");
                return;
            }

            if (Boolean.TRUE.equals(t.getCompletionStatus())) {
                System.out.println("Task \"" + t.getTaskName() + "\" is already completed.");
            } else {
                t.setCompletionStatus(true);
                taskList.updateTask(t);
                System.out.println("Task \"" + t.getTaskName() + "\" has been completed.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid ID entered.");
        }
    }

}
