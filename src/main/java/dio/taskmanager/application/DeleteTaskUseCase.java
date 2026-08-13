package dio.taskmanager.application;

import dio.taskmanager.domain.TaskId;
import dio.taskmanager.domain.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteTaskUseCase {

    private final TaskRepository repository;

    public DeleteTaskUseCase(TaskRepository repository) {
        this.repository = repository;
    }

    public boolean execute(UUID id) {
        TaskId taskId = new TaskId(id);
        if (repository.findById(taskId).isEmpty()) {
            return false;
        }

        repository.delete(taskId);
        return true;
    }
}
