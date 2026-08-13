package dio.taskmanager.application;

import dio.taskmanager.application.input.CreateTaskInput;
import dio.taskmanager.application.output.TaskOutput;
import dio.taskmanager.domain.Task;
import dio.taskmanager.domain.TaskRepository;
import dio.taskmanager.domain.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateTaskUseCaseTest {
    @Mock
    private TaskRepository repository;

    private CreateTaskUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateTaskUseCase(repository);
        when(repository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void shouldCreateAndSaveTaskWithDescription() {
        CreateTaskInput input = new CreateTaskInput("Study Java", Optional.of("Complete the unit tests"));

        TaskOutput output = useCase.execute(input);

        Task savedTask = capturedSavedTask();
        assertAll(
                () -> assertNotNull(savedTask.getId()),
                () -> assertEquals("Study Java", savedTask.getTitle()),
                () -> assertEquals(Optional.of("Complete the unit tests"), savedTask.getDescription()),
                () -> assertEquals(TaskStatus.Pending, savedTask.getStatus()),
                () -> assertEquals(savedTask.getId().toString(), output.id()),
                () -> assertEquals(savedTask.getTitle(), output.title()),
                () -> assertEquals(savedTask.getDescription(), output.description()),
                () -> assertEquals(savedTask.getStatus().name(), output.status())
        );
    }

    @Test
    void shouldCreateAndSaveTaskWithoutDescription() {
        CreateTaskInput input = new CreateTaskInput("Write tests", Optional.empty());

        useCase.execute(input);

        Task savedTask = capturedSavedTask();
        assertAll(
                () -> assertEquals("Write tests", savedTask.getTitle()),
                () -> assertFalse(savedTask.getDescription().isPresent()),
                () -> assertEquals(TaskStatus.Pending, savedTask.getStatus())
        );
    }

    private Task capturedSavedTask() {
        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(repository).save(taskCaptor.capture());
        return taskCaptor.getValue();
    }
}
