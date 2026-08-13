package dio.taskmanager.infra.web;

import dio.taskmanager.application.CreateTaskUseCase;
import dio.taskmanager.application.DeleteTaskUseCase;
import dio.taskmanager.application.GetTaskByIdUseCase;
import dio.taskmanager.application.ListTasksUseCase;
import dio.taskmanager.application.input.CreateTaskInput;
import dio.taskmanager.application.output.TaskOutput;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;
    private final GetTaskByIdUseCase getTaskByIdUseCase;
    private final ListTasksUseCase listTasksUseCase;

    public TaskController(
            CreateTaskUseCase createTaskUseCase,
            DeleteTaskUseCase deleteTaskUseCase,
            GetTaskByIdUseCase getTaskByIdUseCase,
            ListTasksUseCase listTasksUseCase
    ) {
        this.createTaskUseCase = createTaskUseCase;
        this.deleteTaskUseCase = deleteTaskUseCase;
        this.getTaskByIdUseCase = getTaskByIdUseCase;
        this.listTasksUseCase = listTasksUseCase;
    }

    @PostMapping
    public ResponseEntity<TaskOutput> create(@Valid @RequestBody CreateTaskInput input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createTaskUseCase.execute(input));
    }

    @GetMapping
    public List<TaskOutput> list() {
        return listTasksUseCase.execute();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskOutput> getById(@PathVariable UUID id) {
        return ResponseEntity.of(getTaskByIdUseCase.execute(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        return deleteTaskUseCase.execute(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
