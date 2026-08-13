package dio.taskmanager.application;

import dio.taskmanager.domain.Task;
import dio.taskmanager.domain.TaskId;
import dio.taskmanager.domain.TaskRepository;
import dio.taskmanager.domain.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteTaskUseCaseTest {

    @Mock
    private TaskRepository repository;

    private DeleteTaskUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeleteTaskUseCase(repository);
    }

    @Test
    void shouldDeleteExistingTask() {
        UUID id = UUID.randomUUID();
        TaskId taskId = new TaskId(id);
        Task task = new Task(taskId, "Study Java", Optional.empty(), TaskStatus.Pending);
        when(repository.findById(taskId)).thenReturn(Optional.of(task));

        boolean deleted = useCase.execute(id);

        assertTrue(deleted);
        verify(repository).delete(taskId);
    }

    @Test
    void shouldNotDeleteMissingTask() {
        UUID id = UUID.randomUUID();
        TaskId taskId = new TaskId(id);
        when(repository.findById(taskId)).thenReturn(Optional.empty());

        boolean deleted = useCase.execute(id);

        assertFalse(deleted);
        verify(repository, never()).delete(taskId);
    }
}
