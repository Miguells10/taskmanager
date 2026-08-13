package dio.taskmanager.application.output;

import java.util.Optional;

import dio.taskmanager.domain.Task;

public record TaskOutput(String id, String title, Optional<String> description, String status) {
    public static TaskOutput from(Task task) {
        return new TaskOutput(
                task.getId().toString(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name()
        );
    }
}
