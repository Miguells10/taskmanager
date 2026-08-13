package dio.taskmanager.application;

import dio.taskmanager.application.output.TaskOutput;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetTaskByIdUseCaseTest {

    @Mock
    private TaskRepository repository;

    private GetTaskByIdUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetTaskByIdUseCase(repository);
    }

    @Test
    void shouldReturnTaskWhenItExists() {
        UUID id = UUID.randomUUID();
        Task task = new Task(new TaskId(id), "Study Java", Optional.empty(), TaskStatus.Pending);
        when(repository.findById(new TaskId(id))).thenReturn(Optional.of(task));

        Optional<TaskOutput> output = useCase.execute(id);

        assertEquals(Optional.of(TaskOutput.from(task)), output);
        verify(repository).findById(new TaskId(id));
    }

    @Test
    void shouldReturnEmptyWhenTaskDoesNotExist() {
        UUID id = UUID.randomUUID();
        when(repository.findById(new TaskId(id))).thenReturn(Optional.empty());

        Optional<TaskOutput> output = useCase.execute(id);

        assertTrue(output.isEmpty());
        verify(repository).findById(new TaskId(id));
    }
}
