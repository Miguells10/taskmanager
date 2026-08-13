package dio.taskmanager.infra.repository;

import dio.taskmanager.domain.Task;
import dio.taskmanager.domain.TaskId;
import dio.taskmanager.domain.TaskRepository;

import java.util.*;

public class InMemoryTaskRepositoryImpl implements TaskRepository {
    private final Map<TaskId, Task> storage = new HashMap<>();
    @Override
    public Task save(Task task) {
        storage.put(task.getId(), task);
        return task;
    }

    @Override
    public List<Task> findAlll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Task> findById(TaskId id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void delete(TaskId id) {
        storage.remove(id);

    }
}
