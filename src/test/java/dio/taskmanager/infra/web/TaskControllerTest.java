package dio.taskmanager.infra.web;

import dio.taskmanager.application.CreateTaskUseCase;
import dio.taskmanager.application.GetTaskByIdUseCase;
import dio.taskmanager.application.ListTasksUseCase;
import dio.taskmanager.application.output.TaskOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private CreateTaskUseCase createTaskUseCase;

    @Mock
    private GetTaskByIdUseCase getTaskByIdUseCase;

    @Mock
    private ListTasksUseCase listTasksUseCase;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(
                new TaskController(createTaskUseCase, getTaskByIdUseCase, listTasksUseCase)
        ).build();
    }

    @Test
    void shouldCreateTask() throws Exception {
        TaskOutput output = new TaskOutput("task-id", "Study Java", Optional.of("Complete the lesson"), "Pending");
        when(createTaskUseCase.execute(any())).thenReturn(output);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"Study Java","description":"Complete the lesson"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("task-id"))
                .andExpect(jsonPath("$.title").value("Study Java"))
                .andExpect(jsonPath("$.description").value("Complete the lesson"))
                .andExpect(jsonPath("$.status").value("Pending"));

        verify(createTaskUseCase).execute(any());
    }

    @Test
    void shouldRejectTaskWithoutTitle() throws Exception {
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"","description":"Complete the lesson"}
                                """))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(createTaskUseCase);
    }

    @Test
    void shouldListTasks() throws Exception {
        when(listTasksUseCase.execute()).thenReturn(List.of(
                new TaskOutput("first-id", "Study Java", Optional.empty(), "Pending"),
                new TaskOutput("second-id", "Write tests", Optional.empty(), "IN_PROGRESS")
        ));

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("first-id"))
                .andExpect(jsonPath("$[1].id").value("second-id"));

        verify(listTasksUseCase).execute();
    }

    @Test
    void shouldReturnTaskById() throws Exception {
        UUID id = UUID.randomUUID();
        when(getTaskByIdUseCase.execute(id)).thenReturn(Optional.of(
                new TaskOutput(id.toString(), "Study Java", Optional.empty(), "Pending")
        ));

        mockMvc.perform(get("/tasks/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.title").value("Study Java"));

        verify(getTaskByIdUseCase).execute(id);
    }

    @Test
    void shouldReturnNotFoundWhenTaskDoesNotExist() throws Exception {
        UUID id = UUID.randomUUID();
        when(getTaskByIdUseCase.execute(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/tasks/{id}", id))
                .andExpect(status().isNotFound());

        verify(getTaskByIdUseCase).execute(id);
    }
}
