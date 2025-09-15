package entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", nullable = false)
    private Integer id;

    @Column(name = "task_name", nullable = false)
    private String taskName;

    @ColumnDefault("0")
    @Column(name = "completion_status", nullable = false)
    private Boolean completionStatus = false;

    public Integer getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Boolean getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(Boolean completionStatus) {
        this.completionStatus = completionStatus;
    }

    public String getTaskInformation() {
        return "ID: " + id + " | Task Name: " + taskName + " | Status: " + (completionStatus ? "✓ Complete" : "✗ Incomplete");
    }


}