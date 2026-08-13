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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListTasksUseCaseTest {

    @Mock
    private TaskRepository repository;

    private ListTasksUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ListTasksUseCase(repository);
    }

    @Test
    void shouldReturnAllSavedTasks() {
        Task firstTask = new Task(new TaskId(), "Study Java", Optional.empty(), TaskStatus.Pending);
        Task secondTask = new Task(new TaskId(), "Write tests", Optional.of("Create unit tests"), TaskStatus.IN_PROGRESS);
        when(repository.findAll()).thenReturn(List.of(firstTask, secondTask));

        List<TaskOutput> tasks = useCase.execute();

        assertEquals(List.of(TaskOutput.from(firstTask), TaskOutput.from(secondTask)), tasks);
        verify(repository).findAll();
    }

    @Test
    void shouldReturnEmptyListWhenThereAreNoTasks() {
        when(repository.findAll()).thenReturn(List.of());

        List<TaskOutput> tasks = useCase.execute();

        assertEquals(List.of(), tasks);
        verify(repository).findAll();
    }
}
