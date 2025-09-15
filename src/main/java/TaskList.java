import entity.Task;

import java.util.ArrayList;

public class TaskList extends ArrayList<Task> {

    public void getCompletedTasks() {
        TaskList completed = new TaskList();

        if (this.isEmpty()) {
            System.out.println("No tasks have been completed. Get started now!");
            return;
        }

        for (Task task : this) {
            if (task.getCompletionStatus()) {
                completed.add(task);
            }
        }

        if (completed.isEmpty()) {
            System.out.println("No tasks have been completed. Get started now!");
        } else {
            for (Task task : completed) {
                System.out.println(task.getTaskInformation());
            }
        }
    }

    public void getIncompleteTasks() {
        TaskList incomplete = new TaskList();

        if (this.isEmpty()) {
            System.out.println("There are no incomplete tasks.");
            return;
        }

        for (Task task : this) {
            if (!task.getCompletionStatus()) {
                incomplete.add(task);
            }
        }

        if (incomplete.isEmpty()) {
            System.out.println("No incomplete tasks remaining! Great job!");
        } else {
            for (Task task : incomplete) {
                System.out.println(task.getTaskInformation());
            }
        }
    }
}