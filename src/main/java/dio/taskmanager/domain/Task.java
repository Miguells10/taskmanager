package dio.taskmanager.domain;

import org.springframework.util.Assert;

import java.util.Optional;

public class Task {

    private TaskId id;
    private String title;
    private Optional<String> description;
    private TaskStatus status;

    public Task(TaskId id, String title, Optional<String> description, TaskStatus status) {
        Assert.notNull(title, "Title must not be nulll");
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public Task(){
    }

    public TaskId getId() {
        return id;
    }

    public void setId(TaskId id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public void setDescription(Optional<String> description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
