package dio.taskmanager.application.input;

import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

public record CreateTaskInput(@NotBlank String title, Optional<String> description) {
}
