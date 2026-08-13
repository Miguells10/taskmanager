package dio.taskmanager.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class TaskRepositoryTest {

    private TaskRepository repository;

    protected abstract TaskRepository createRepository();

    @BeforeEach
    void setUp() {
        repository = createRepository();
    }

    @Test
    void shouldSaveTask() {
        Task task = newTask("Study Java");

        Task savedTask = repository.save(task);

        assertEquals(task, savedTask);
    }

    @Test
    void shouldFindAllSavedTasks() {
        Task firstTask = newTask("Study Java");
        Task secondTask = newTask("Write tests");
        repository.save(firstTask);
        repository.save(secondTask);

        var tasks = repository.findAlll();

        assertEquals(2, tasks.size());
        assertTrue(tasks.containsAll(java.util.List.of(firstTask, secondTask)));
    }

    @Test
    void shouldFindTaskById() {
        Task task = newTask("Study Java");
        repository.save(task);

        Optional<Task> foundTask = repository.findById(task.getId());

        assertTrue(foundTask.isPresent());
        assertEquals(task, foundTask.get());
    }

    @Test
    void shouldReturnEmptyWhenTaskDoesNotExist() {
        Optional<Task> foundTask = repository.findById(new TaskId());

        assertTrue(foundTask.isEmpty());
    }

    @Test
    void shouldDeleteTaskById() {
        Task task = newTask("Study Java");
        repository.save(task);

        repository.delete(task.getId());

        assertFalse(repository.findById(task.getId()).isPresent());
    }

    private Task newTask(String title) {
        return new Task(new TaskId(), title, Optional.empty(), TaskStatus.Pending);
    }

}
