package dio.taskmanager.infra.web;

import dio.taskmanager.application.CreateTaskUseCase;
import dio.taskmanager.application.ListTasksUseCase;
import dio.taskmanager.application.input.CreateTaskInput;
import dio.taskmanager.application.output.TaskOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final ListTasksUseCase listTasksUseCase;

    public TaskController(CreateTaskUseCase createTaskUseCase, ListTasksUseCase listTasksUseCase) {
        this.createTaskUseCase = createTaskUseCase;
        this.listTasksUseCase = listTasksUseCase;
    }

    @PostMapping
    public ResponseEntity<TaskOutput> create(@RequestBody CreateTaskInput input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createTaskUseCase.execute(input));
    }

    @GetMapping
    public List<TaskOutput> list() {
        return listTasksUseCase.execute();
    }
}
