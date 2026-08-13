package dio.taskmanager.domain;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Task save(Task task);
    List<Task> findAlll();
    Optional<Task> findById(TaskId id);
    void delete(TaskId id);

}
