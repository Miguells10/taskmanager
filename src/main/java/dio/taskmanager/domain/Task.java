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
        Assert.notNull(title, "Title must not be nulll");
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public Task() {
    }

}
