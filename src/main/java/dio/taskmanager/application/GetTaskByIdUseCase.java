package dio.taskmanager.application;

import dio.taskmanager.application.output.TaskOutput;
import dio.taskmanager.domain.TaskId;
import dio.taskmanager.domain.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetTaskByIdUseCase {

    private final TaskRepository repository;

    public GetTaskByIdUseCase(TaskRepository repository) {
        this.repository = repository;
    }

    public Optional<TaskOutput> execute(UUID id) {
        return repository.findById(new TaskId(id)).map(TaskOutput::from);
    }
}
