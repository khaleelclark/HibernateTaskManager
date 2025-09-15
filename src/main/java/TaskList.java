import entity.Task;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class TaskList extends ArrayList<Task> {

    private final EntityManager em;

    public TaskList(EntityManager em) {
        this.em = em;
    }

    public void loadFromDatabase() {
        List<Task> allTasks = em.createQuery("SELECT t FROM Task t", Task.class)
                .getResultList();
        this.addAll(allTasks);
    }

    public void addTask(Task task) {
        em.getTransaction().begin();
        em.persist(task);
        em.getTransaction().commit();
        super.add(task);
    }

    public void removeTask(Task task) {
        em.getTransaction().begin();
        Task managed = em.find(Task.class, task.getId());
        if (managed != null) {
            em.remove(managed);
        }
        em.getTransaction().commit();
        super.remove(task);
    }

    public void updateTask(Task task) {
        em.getTransaction().begin();
        em.merge(task);
        em.getTransaction().commit();
    }

    // Filtered in-memory lists
    public List<Task> getCompletedTasks() {
        List<Task> completed = new ArrayList<>();
        for (Task t : this) if (t.getCompletionStatus()) completed.add(t);
        return completed;
    }

    public List<Task> getIncompleteTasks() {
        List<Task> incomplete = new ArrayList<>();
        for (Task t : this) if (!t.getCompletionStatus()) incomplete.add(t);
        return incomplete;
    }

    public void printCompletedTasks() {
        List<Task> completed = getCompletedTasks();
        if (completed.isEmpty()) System.out.println("No completed tasks. Add some now!");
        else {
            System.out.println("|------------------------Complete Tasks------------------------|");
            completed.forEach(t -> System.out.println(t.getTaskInformation()));
            System.out.println("|--------------------------------------------------------------|");
        }
    }

    public void printIncompleteTasks() {
        List<Task> incomplete = getIncompleteTasks();
        if (incomplete.isEmpty()) System.out.println("No incomplete tasks. Great job!");
        else {
            System.out.println("|-----------------------Incomplete Tasks-----------------------|");
            incomplete.forEach(t -> System.out.println(t.getTaskInformation()));
            System.out.println("|--------------------------------------------------------------|");
        }
    }

    public Task getTaskByID(Integer id) {
        return this.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
