package dio.taskmanager.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

import java.util.Optional;
@Getter
@Setter
public class Task {

    private TaskId id;
    private String title;
    private Optional<String> description;
    private TaskStatus status;

    public Task(TaskId id, String title, Optional<String> description, TaskStatus status) {
        Assert.hasText(title, "Title must not be blank");
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public Task() {
    }

    public Task(String title, Optional<String> description) {
        this(new TaskId(), title, description, TaskStatus.Pending);
    }
}
