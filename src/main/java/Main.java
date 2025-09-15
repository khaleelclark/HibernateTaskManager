import entity.Task;
import jakarta.persistence.*;

public class Main {
    static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    static final EntityManager entityManager = entityManagerFactory.createEntityManager();
    static EntityTransaction transaction = entityManager.getTransaction();
    public static boolean completeTasks = true;
    public static boolean incompleteTasks = false;

    public static void main(String[] args) {
        try (entityManagerFactory; entityManager) {
            transaction.begin();

            getTasksByStatus(completeTasks);
            getTasksByStatus(incompleteTasks);

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        entityManager.close();
    }
    public static void getTasksByStatus (boolean taskStatus) {
        TypedQuery<Task> taskByName = entityManager.createNamedQuery("Task.byStatus", Task.class);
        taskByName.setParameter("status", taskStatus);
        for (Task task : taskByName.getResultList()) {
            System.out.println(task.getTaskName());
        }
    }
}
